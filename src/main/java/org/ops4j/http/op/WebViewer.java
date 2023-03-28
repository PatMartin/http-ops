package org.ops4j.http.op;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.ops4j.OpData;
import org.ops4j.base.BaseOp;
import org.ops4j.exception.OpsException;
import org.ops4j.inf.Op;

import com.google.auto.service.AutoService;

import javafx.application.Application;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@AutoService(Op.class)
@Command(name = "web:view", description = "View a web asset.")
public class WebViewer extends BaseOp<WebViewer>
{
  public static Queue<OpData>   data = new LinkedList<>();

  @Option(names = { "--view" }, description = "The page to view.")
  public @Getter @Setter String view = null;

  public WebViewer()
  {
    super("web:view");
  }

  public List<OpData> execute(OpData input) throws OpsException
  {
    data.add(input);
    return input.asList();
  }

  public static void main(String args[]) throws OpsException
  {
    // OpCLI.cli(new WebViewer(), args);
    Application.launch(BrowserApp.class, args);
  }
}
