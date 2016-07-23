/**
 * 
 */
package driver;

import java.util.EmptyStackException;
import java.util.List;

/**
 * @author Jason
 *
 */
public class Popd implements Command {

  private String output;

  /**
   * This method executes the Popd command. I.E removes the current Directory
   * from the DirectoryStack and changes the current Directory to this one.
   */
  @Override
  public String execute(List<String> args, Shell shell) {
    // Retrieve the last directory if there is one
    try {
      Directory newDir = shell.getDirStack().pop();
      // Get the last fullPath corresponding to the last directory
      String newFullPath = shell.getDirStack().getLastFullPath();
      // Change the current directory to the most recent one
      shell.changeFullPath(newFullPath);
      shell.setCurrentDirectory(newDir);
      output = "Succesfully changed the current working directory to the "
          + "most recent on the stack";
    } catch (EmptyStackException e) {
      output = "The stack is empty, no previous directory to change into";
    }
    return output;
  }
}
