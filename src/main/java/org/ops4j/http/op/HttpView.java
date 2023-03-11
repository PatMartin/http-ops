package org.ops4j.http.op;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ops4j.OpData;
import org.ops4j.base.BaseOp;
import org.ops4j.cli.OpCLI;
import org.ops4j.exception.OpsException;
import org.ops4j.inf.Op;

import com.google.auto.service.AutoService;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@AutoService(Op.class)
@Command(name = "http:view", description = "Stream data through a http view.")
public class HttpView extends BaseOp<HttpView>
{
  @Parameters(index = "0", arity = "0..*",
      description = "The number of milliseconds to pause.")
  private @Getter @Setter Map<String, String> map = new HashMap<>();

  @Option(names = { "--view" }, description = "The http view.")
  public @Getter @Setter String               httpView = null;

  public class WebViewer extends Application
  {
    public void start(Stage primaryStage)
    {
      primaryStage.setTitle("JavaFX WebView Example");

      WebView webView = new WebView();

      webView.getEngine().load("http://google.com");

      VBox vBox = new VBox(webView);
      Scene scene = new Scene(vBox, 960, 600);

      primaryStage.setScene(scene);
      primaryStage.show();
    }
  }

  private WebViewer viewer = null;
  
  public HttpView()
  {
    super("http:view");
  }

  public HttpView initialize() throws OpsException
  {
    return this;
  }

  public HttpView open() throws OpsException
  {
    viewer = new WebViewer();
    viewer.start(new Stage());
    return this;
  }

  public List<OpData> execute(OpData input)
  {
    return input.asList();
  }

  public HttpView close() throws OpsException
  {
    try
    {
      viewer.stop();
    }
    catch(Exception ex)
    {
      throw new OpsException(ex);
    }
    return this;
  }

  public static void main(String args[]) throws OpsException
  {
    //launch(WebViewer.class);
    OpCLI.cli(new HttpView(), args);
  }
}
