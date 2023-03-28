package org.ops4j.http.op;

import java.util.ArrayList;
import java.util.List;

import org.ops4j.OpData;
import org.ops4j.http.JavaBridge;

//import org.ops4j.http.JavaBridge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class BrowserApp extends Application
{
  private static List<OpData> data      = new ArrayList<OpData>();

  private WebView             webView   = null;
  private WebEngine           webEngine = null;

  // Maintain a strong reference to prevent garbage collection:
  // https://bugs.openjdk.java.net/browse/JDK-8154127
  private final JavaBridge    bridge    = new JavaBridge();

  public static void add(OpData opdata)
  {
    data.add(opdata);
  }

  private void enableFirebug(final WebEngine we)
  {
    we.executeScript(
        "if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', '../javascript/firebug/latest/firebug-lite.min.js#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', '../javascript/firebug/latest/firebug-lite.min.js#startOpened');}");
  }

  @Override
  public void start(Stage primaryStage) throws Exception
  {
    primaryStage.setTitle("Ops4j WebView");

    webView = new WebView();
    webEngine = webView.getEngine();

    // webView.getEngine().setJavaScriptEnabled(true);
    webEngine.load("http://localhost:4242/index.html");
    enableFirebug(webEngine);

    webEngine.getLoadWorker().stateProperty()
        .addListener((observable, oldValue, newValue) -> {
          JSObject window = (JSObject) webEngine.executeScript("window");

          System.out.println("WINDOW-CLASS: " + window.getClass().getName());
          window.setMember("java", bridge);
          window.setMember("data", data);
          Object obj = webEngine
              .executeScript("console.log = function(message)\n{\n"
                  + " java.log(message);\n};");
          System.out.println("OBJECT: " + obj);
        });

    VBox vBox = new VBox(webView);
    Scene scene = new Scene(vBox, 960, 600);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
