/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 *
 */
public class ChangeDirectory implements Command {
  /**
   * This method executes the cd command, I.e changes the current directory
   */
  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidPathException, InvalidSyntaxException, NoParentException {
    // Get the current directory and root directory
    Directory currDir = shell.getCurrentDirectory();
    Directory rootDir = shell.getRootDirectory();
    // Get the current absolute path
    String fullPath = shell.getFullPath();
    // Check if too many arguments are passed in
    if (args.size() == 0) {
      throw new InvalidSyntaxException("The syntax is invalid");
    }
    // Check if the new path is a relative or absolute
    String path = args.get(0);
    if (path.startsWith("/")) {
      // The path is absolute, check if it exists
      if (rootDir.isChildPath(args.get(0).substring(1), "directory")) {
        // Change the fullPath to include the new path
        shell.changeFullPath(path);
        // Now retrieve the directory object
        Directory newCurr = rootDir.getPathDirectory(path.substring(1));
        // Set the current directory to the retrieved object
        shell.setCurrentDirectory(newCurr);
      } else {
        throw new InvalidPathException("Not a valid path");
      }
    }
    // The path is relative
    else {
      // Check if the requested path is .
      if (args.get(0).equals(".")) {
        return "";
      }
      // Change into the previous directory
      else if (args.get(0).equals("..")) {
        // Get the last folder
        String currPath = shell.getFullPath();
        // if currPath is length 1, then we are home and there is no
        // parent
        if (currPath.length() == 1) {
          throw new NoParentException("There is no parent directory");
        }
        // Remove the last "/"
        String previousPath = currPath.substring(0, (currPath.length() - 1));
        // Set the fullPath to the new path
        shell.changeFullPath(
            previousPath.substring(0, previousPath.lastIndexOf("/") + 1));
        // Get the directory object
        Directory newCurr = rootDir.getPathDirectory(
            previousPath.substring(0, previousPath.lastIndexOf("/") + 1));
        // Set the current directory
        shell.setCurrentDirectory(newCurr);
      }
      // Check if the path exists
      else if (currDir.isChildPath(args.get(0), "directory")) {
        // Change the full path to include the new path
        // Split at the slashes
        String[] newPath;
        newPath = path.split("/");
        path = "";
        // Loop through each directory name
        for (String nextDir : newPath) {
          // If the directory is a . or ..
          if (!nextDir.equals("..") && !nextDir.equals(".")) {
            path += nextDir + "/";
          }
        }
        shell.changeFullPath(fullPath + path);
        // Get the new directory object
        Directory newCurr = currDir.getPathDirectory(path);
        // Set the new current directory
        shell.setCurrentDirectory(newCurr);
      } else {
        throw new InvalidPathException("Not a valid path");
      }
    }
    return "";
  }
}
