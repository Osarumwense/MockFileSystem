/**
 * 
 */
package driver;

import java.util.List;

/**
 * @author Jason
 *
 */
public class MakeDirectory implements Command {
  /**
   * This method executes the mkdir command, I.E it creates a new Directory
   * 
   * @throws InvalidPathException
   */
  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidPathException {
    Directory dir;
    int directoryNum = 0;
    boolean pathGiven;
    String directoryName;
    // Create a list of invalid characters in a directory name
    String[] invalidChars = new String[] {"!", "@", "$", "&", "*", "(", ")",
        "?", ":", "[", "]", "<", ">", "'", "`", "|", "=", "{", "}", "\",", "/",
        ",", ";", ".", "\""};
    while (directoryNum < args.size()) {
      // Check if the directory has a /, if it does, then a path is given
      if (args.get(directoryNum).contains("/")) {
        pathGiven = true;
        // Get the new directory name
        directoryName = args.get(directoryNum)
            .substring(args.get(directoryNum).lastIndexOf("/") + 1);
      } else {
        pathGiven = false;
        directoryName = args.get(directoryNum);
      }
      // Check if the new directory name is valid
      for (String nextChar : invalidChars) {
        // If the new directory name contains an invalid character
        if (directoryName.contains(nextChar)) {
          return "A directory cannot contain character: " + nextChar;
        }
      }
      if (pathGiven) {
        // Get the last element in args, since it is the path
        String path = args.get(directoryNum);
        // Check if path is absolute or relative
        if (path.startsWith("/")) {
          // Get the root directory and check if the path is valid
          Directory rootDir = shell.getRootDirectory();
          String checkPath;
          int lastSlash = path.lastIndexOf("/");
          if (lastSlash == 0) {
            checkPath = "";
          } else {
            checkPath = path.substring(1, lastSlash);
          }
          if (!checkPath.isEmpty()) {
            if (rootDir.isChildPath(checkPath, "directory")) {
              // Get the object
              dir = rootDir.getPathDirectory(path.substring(1));
            } else {
              throw new InvalidPathException(
                  "Path " + path + " does not exist");
            }
          } else {
            // The directory is the current
            dir = shell.getCurrentDirectory();
          }
        }
        // Otherwise the path is relative
        else {
          // Get the current directory and check if the given path is
          // valid
          Directory currDir = shell.getCurrentDirectory();
          // Get where the directory path ends
          int lastSlashIndex;
          lastSlashIndex = path.lastIndexOf("/");
          // There is more directories to traverse
          if (lastSlashIndex != -1) {
            path = path.substring(0, lastSlashIndex);
          }
          // There is no slash so the new directory is to be created
          // in current
          else {
            path = "";
          }
          if (currDir.isChildPath(path, "directory")) {
            // Get the object
            dir = currDir.getPathDirectory(path);
          } else {
            throw new InvalidPathException("Path " + path + " does not exist");
          }
        }
      }
      // There is no path given
      else {
        dir = shell.getCurrentDirectory();
      }
      // Check if the directory requested already has one with the same
      // name
      if (dir.dirExists(directoryName) || dir.fileExists(directoryName)) {
        // Print the error message
        System.out.println(
            "A directory/file with name: " + directoryName + " exists already");
      } else {
        // Create the new object
        Directory newDir = new Directory(directoryName);
        // Add it as a child of the requested path directory object
        dir.addChildDirectory(newDir);
        // Add dir as the parent of newDir
        newDir.setParent(dir);
      }
      directoryNum++;
    }
    // Get a list of the directory names
    String returnMessage = "Directory name(s): ";
    for (int i = 0; i < args.size(); i++) {
      String dirName;
      int lastSlashValue;
      lastSlashValue = args.get(i).lastIndexOf("/");
      // If there is no slash in the string
      if (lastSlashValue == -1) {
        dirName = args.get(i);
      } else {
        dirName = args.get(i).substring(lastSlashValue + 1);
      }
      returnMessage = returnMessage + dirName;
      // If it is the last directory name, no need for a comma
      if (!(i == directoryNum - 1)) {
        returnMessage = returnMessage + ",";
      }
    }
    return "";
  }
}
