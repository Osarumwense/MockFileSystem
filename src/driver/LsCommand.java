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
public class LsCommand implements Command {

  /**
   * This method executes the Ls command, I.e lists the contents of the
   * Directory or prints the file name
   * 
   * @throws InvalidPathException
   */
  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidPathException {
    // Initialize the number of directories to loop through
    int directoryNum = 0;
    // The output string
    String outputString = "";
    // Check if there is a path given
    if (args.size() != 0) {
      // If there is a no -r or -R passed in
      if (!args.get(0).equals("-r") && !args.get(0).equals("-R")) {
        while (directoryNum < args.size()) {
          // Check if the path given is relative or a full path
          if (args.get(directoryNum).startsWith("/")) {
            // Strip the "/" since the isChildPath syntax is not satisfied
            String path = args.get(directoryNum).substring(1);
            // Check if it is a valid path by using the root directory
            // method
            Directory rootDir = shell.getRootDirectory();
            // If it is a valid path, get the directory object of that path
            if (rootDir.isChildPath(path, "directory")) {
              Directory neededDir = rootDir.getPathDirectory(path);
              outputString += (neededDir.getDirectoryName() + ":\n"
                  + neededDir.printAllChildren() + "\n");
            }
            // Otherwise check if it is a file
            else if (rootDir.isChildPath(path, "file")) {
              File neededFile = rootDir.getPathFile(path);
              outputString += (neededFile.getFileName());
            }
            // None of the above cases is satisfied, so the path is invalid
            System.out.print("Invalid path given");
          }
          // Otherwise the given path is relative
          else {
            // Get the current directory object
            Directory currDir = shell.getCurrentDirectory();
            // Check if the given path is valid
            if (currDir.isChildPath(args.get(directoryNum), "directory")) {
              // Return the contents of the directory
              Directory neededDir =
                  currDir.getPathDirectory(args.get(directoryNum));
              outputString += neededDir.getDirectoryName() + ": \n"
                  + neededDir.printAllChildren();
            }
            // If the path given is a file
            else if (currDir.isChildPath(args.get(directoryNum), "file")) {
              File neededFile = currDir.getPathFile(args.get(directoryNum));
              outputString += neededFile.getFileName();
            } else {
              // None of the above cases is satisfied, so the path is invalid
              System.out.print("Invalid path given");
            }
          }
          directoryNum++;
        }
      }
      // If -r or -R is passed in, get all of the directories contents using
      // the helper function
      else if (args.get(0).equals("-r") || args.get(0).equals("-R")) {
        // If there is a path given, use the right helper
        if (args.size() > 1) {
          directoryNum = 1;
          while (directoryNum < args.size()) {
            Directory neededDir;
            // Get the requested directory
            if (args.get(directoryNum).startsWith("/")) {
              // If it is a valid path
              if (shell.getRootDirectory().isChildPath(
                  (args.get(directoryNum).substring(1)), "directory")) {
                // Get the needed directory
                neededDir = shell.getRootDirectory()
                    .getPathDirectory(args.get(directoryNum).substring(1));
              }
              // Check if the path is valid
              else if (shell.getRootDirectory()
                  .isChildPath(args.get(directoryNum).substring(1), "file")) {
                // Get the needed file
                File neededFile = shell.getRootDirectory()
                    .getPathFile(args.get(directoryNum).substring(1));
                outputString += neededFile.getFileName();
              }
              // If the path does not exist
              else {
                System.out.println("Not a valid path");
              }
            } else {
              // Check if the path is valid
              if (shell.getCurrentDirectory()
                  .isChildPath(args.get(directoryNum), "directory")) {
                // Get the directory
                neededDir = shell.getCurrentDirectory()
                    .getPathDirectory(args.get(directoryNum));
                outputString += neededDir.printAllChildren() + "\n"
                    + lsWithRHelper(neededDir,
                        neededDir.getChildrenDirectories(),
                        args.get(directoryNum));
              }
              // Check if the path is valid
              else if (shell.getCurrentDirectory()
                  .isChildPath(args.get(directoryNum), "file")) {
                // Get the file
                File neededFile = shell.getCurrentDirectory()
                    .getPathFile(args.get(directoryNum));
                outputString += neededFile.getFileName();
              }
              // If the path is not existent
              else {
                System.out.println("Not a valid path");
              }
            }
            directoryNum++;
          }
        } else if (args.size() == 1) {
          outputString += shell.getCurrentDirectory().printAllChildren() + "\n"
              + lsWithRHelper(shell.getCurrentDirectory(),
                  shell.getCurrentDirectory().getChildrenDirectories(),
                  shell.getCurrentDirectory().getDirectoryName());
        }
      }
    } else {
      // For when no path is given, get the current directory
      Directory currDir = shell.getCurrentDirectory();
      // Return its contents
      outputString += currDir.printAllChildren();
    }
    // Return its contents
    return outputString;
  }

  private String lsWithRHelper(Directory currDir,
      ArrayList<Directory> currDirChildren, String currentPath) {
    // The contents of the directory
    String contents = "";
    // Base case
    if (currDirChildren.isEmpty()) {
      return contents;
    } else {
      // Loop through all of the children directories
      for (Directory nextChild : currDir.getChildrenDirectories()) {
        // If current path is the root, no need to add a slash
        if (currentPath.equals("/")) {
          contents += currentPath + nextChild.getDirectoryName() + ": \n"
              + nextChild.printAllChildren();
          // Recurse
          contents +=
              lsWithRHelper(nextChild, nextChild.getChildrenDirectories(),
                  currentPath + nextChild.getDirectoryName());
        } else {
          contents += currentPath + "/" + nextChild.getDirectoryName() + ": \n"
              + nextChild.printAllChildren() + "\n";
          // Recurse
          contents +=
              lsWithRHelper(nextChild, nextChild.getChildrenDirectories(),
                  currentPath + "/" + nextChild.getDirectoryName());
        }
      }
    }
    return contents;
  }
}

