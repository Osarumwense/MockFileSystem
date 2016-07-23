
package driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * A general command interface for every command to from
 * 
 * @author osaru
 *
 */
public interface Command {
  // Every command should be able to execute.
  public String execute(List<String> args, Shell shell)
      throws InvalidPathException, InvalidSyntaxException, NoParentException,
      InvalidFileNameException, InvalidHistoryNumberException,
      MalformedURLException, IOException;

}
