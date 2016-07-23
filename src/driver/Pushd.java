/**
 * 
 */
package driver;

import java.util.List;

/**
 * @author Jason
 *
 */
public class Pushd implements Command {

  /**
   * This method executes the pushd command. I.E it pushes the directory into
   * the stack
   * 
   * @throws InvalidSyntaxException
   * @throws InvalidPathException
   */
  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidSyntaxException, InvalidPathException {
    // Save the current directory
    Directory saveDir = shell.getCurrentDirectory();
    // Check if arguments are given
    if (args.size() == 0) {
      throw new InvalidSyntaxException("Not proper syntax for command pushd");
    }
    // Check if the requested path is valid
    if (args.get(0).startsWith("/")) {
      // The path is absolute
      if (shell.getRootDirectory().isChildPath(args.get(0).substring(1),
          "directory")) {
        // Add the current fullPath
        shell.getDirStack().addFullPath(shell.getFullPath());
        // Change the fullPath to include the new path
        shell.changeFullPath(args.get(0));
        // Now retrieve the directory object
        Directory newCurr =
            shell.getRootDirectory().getPathDirectory(args.get(0).substring(1));
        // Set the current directory to the retrieved object
        shell.setCurrentDirectory(newCurr);
        // Add the saved directory to the stack
        shell.getDirStack().push(saveDir);
      } else {
        throw new InvalidPathException("Not a valid path");
      }
    }
    // The path is relative
    else {
      // Check if the path exists
      if (shell.getCurrentDirectory().isChildPath(args.get(0), "directory")) {
        // Add the current fullPath
        shell.getDirStack().addFullPath(shell.getFullPath());
        // Change the fullpath to include the new path
        shell.changeFullPath(shell.getFullPath() + args.get(0) + "/");
        // Retrieve the directory object to change into
        Directory newCurr =
            shell.getCurrentDirectory().getPathDirectory(args.get(0));
        // Set the current directory
        shell.setCurrentDirectory(newCurr);
        // Add the saved directory
        shell.getDirStack().push(saveDir);
      } else {
        throw new InvalidPathException("Not a valid path");
      }
    }
    return "The directory has been saved and the current working directory "
        + "is now " + shell.getFullPath();
  }

}
