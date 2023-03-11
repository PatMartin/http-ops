package org.ops4j.http.op;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.ops4j.OpData;
import org.ops4j.base.BaseOp;
import org.ops4j.cli.OpCLI;
import org.ops4j.exception.OpsException;
import org.ops4j.inf.Op;

import com.google.auto.service.AutoService;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@AutoService(Op.class) @Command(name = "http:driver",
    description = "Stream data through a http driver.")
public class HttpDriver extends BaseOp<HttpDriver>
{
  @Parameters(index = "0", arity = "0..*",
      description = "Name value pairs representing options to be sent "
          + "into the web view.")
  private @Getter @Setter Map<String, String> map        = new HashMap<>();

  @Option(names = { "--view" }, description = "The http view.")
  public @Getter @Setter String               httpView   = null;

  @Option(names = { "--driver" }, description = "The web driver.")
  public @Getter
  @Setter String                              driverPath = "C:/data/drivers/chromedriver.exe";

  private WebDriver                           driver     = null;

  public HttpDriver()
  {
    super("http:driver");
  }

  public HttpDriver initialize() throws OpsException
  {
    System.setProperty("webdriver.chrome.driver", getDriverPath());
    driver = new ChromeDriver();

    driver.get("http://localhost:4242/index.html");

    try
    {
      Thread.sleep(15000);
    }
    catch(InterruptedException ex)
    {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    } // Let the user actually see something!

    return this;
  }

  public HttpDriver open() throws OpsException
  {
    return this;
  }

  public List<OpData> execute(OpData input)
  {
    return input.asList();
  }

  public HttpDriver close() throws OpsException
  {
    driver.quit();
    return this;
  }

  public static void main(String args[]) throws OpsException
  {
    // launch(WebViewer.class);
    OpCLI.cli(new HttpDriver(), args);
  }
}
