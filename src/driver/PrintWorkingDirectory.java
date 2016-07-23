/**
 * 
 */
package driver;

import java.util.List;

/**
 * @author Jason
 *
 * @param args The arguments that the command needs
 * @param shell The shell object
 */
public class PrintWorkingDirectory implements Command {

  /**
   * This method executes the pwd command. I.E it prints the current working
   * Directory
   */
  @Override
  public String execute(List<String> args, Shell shell) {
    // Return the current directory path
    return shell.getFullPath();
  }

}
