/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent the mv command used to rename path
 * 
 * @author Ruyin Zhang
 */
public class Move implements Command {

  /**
   * This method executes the mv command as requested by the user
   * 
   * @throws InvalidSyntaxException
   */
  @Override
  public String execute(List<String> argsList, Shell shell)
      throws InvalidSyntaxException {
    String output = " ";
    // initialize the boolean variable validPath to be false
    boolean validPath = false;
    // initialize an empty directory object for storing the new directory
    // object later
    Directory newdir = new Directory("");
    // initialize an empty directory for storing the old directory object later
    Directory olddir = new Directory("");
    // initialize an empty directory for storing the old directory's parent
    // directory object later
    Directory Parent_olddir = new Directory("");
    // initialize an empty file object for storing the old file object later
    File oldfile = new File("", "");
    // initialize a string type variable to record the directory path
    String directoryPath;
    // initialize a string type variable to record the new name
    String newname;
    // initialize a string type variable to record the old name
    String oldname;


    try {
      // First check if the syntax entered is correct
      argsList = argumentCheck(argsList, shell);
      String oldpath = argsList.get(0);
      String oldpathname = argsList.get(0);
      String newpath = argsList.get(1);
      String newpathname = argsList.get(1);

      // convert the relative old path to the absolute path
      if (oldpath.charAt(0) != ('/')) {
        oldpath = shell.getFullPath() + oldpath;
      }
      // convert the relative new path to the absolute path
      if (newpath.charAt(0) != ('/')) {
        newpath = shell.getFullPath() + newpath;
      }

      // find out the parent directory of the oldPath
      int oldfileStart = oldpath.lastIndexOf('/', oldpath.length() - 1);
      oldname = oldpath.substring(oldfileStart + 1);
      if (oldfileStart == 0) {
        directoryPath = "/";
      } else {
        directoryPath = oldpath.substring(0, oldfileStart);
      }
      Parent_olddir =
          shell.getRootDirectory().getPathDirectory(directoryPath.substring(1));

      // the following block of code, we are going to do two things
      // 1) Check if the oldPath is an existence path or not
      // 2) If the oldPath is an existence path, get the oldPath object out

      // if the oldpath is a file
      if (shell.getRootDirectory().isChildPath(oldpath.substring(1), "file")) {
        validPath = true;
        // get the oldpath object out
        oldfile = shell.getRootDirectory().getPathFile(oldpath.substring(1));
      }
      // if the oldpath is a directory
      if (shell.getRootDirectory().isChildPath(oldpath.substring(1),
          "directory")) {
        validPath = true;
        // get the oldpath object out
        olddir =
            shell.getRootDirectory().getPathDirectory(oldpath.substring(1));

      }
      // the oldPath is not an existence path, throws error message
      if (!validPath) {
        throw new InvalidPathException(
            "mv: cannot stat '" + oldpathname + "': No such file or directory");
      }

      // Below here, we are going to check if the newPath is an existence path
      // or not, so we set the boolean variable validPath back to false
      validPath = false;

      // find the name of the file in the path
      int fileStart = newpath.lastIndexOf('/', newpath.length() - 1);
      newname = newpath.substring(fileStart + 1);
      // find out the parent's path of the new file/directory's name
      // if the last slash is at the first position
      if (fileStart == 0) {
        // the directory path is a root directory
        directoryPath = "/";
        // else the directory path is the path that starts from the first
        // character of the newpath till the last character before the last
        // slash
      } else {
        directoryPath = newpath.substring(0, fileStart);
      }
      // check if the newPath is an existence parent path or not
      if (shell.getRootDirectory().isChildPath(directoryPath.substring(1),
          "directory")) {
        validPath = true;
        // check if the newPath is a file or a directory
        if (shell.getRootDirectory().isChildPath(newpath.substring(1),
            "directory")) {
          // if the newpath is a directory, the oldpath copy is going to
          // add to the current directory path. We use newdir to record this
          // object
          newdir =
              shell.getRootDirectory().getPathDirectory(newpath.substring(1));
        } else {
          // if the newpath is not a directory, the oldpath copy is
          // going to add to its parent's directory. We use newdir to record
          // the object
          // if its parent directory is the root directory
          if (directoryPath == "/") {
            // the oldpath is going to add to the root
            newdir = shell.getRootDirectory();
            // else newdir is the newpath's parent directory
          } else {
            newdir = shell.getRootDirectory()
                .getPathDirectory(directoryPath.substring(1));
          }
          // if olddir is a directory
          if (olddir.getDirectoryName().length() > 0) {
            // set a new name to the old directory
            olddir.setDirectoryName(newname);
            // else the olddir is a file
          } else {
            // set the copy directory's name as the old diretory's name
            oldfile.setFileName(newname);
          }
        }
        // if olddir is a directory
        // remove the indicated directory under the oldpath's parent
        // under the new path, remove the directory with the same name as the
        // oldpath object
        if (olddir.getDirectoryName().length() > 0) {
          //
          olddir.getParent().mvChildDirectory(olddir.getDirectoryName());
          newdir.mvChildDirectory(olddir.getDirectoryName());
          // else olddir is a file
          // remove the indicated file under the oldpath's parent
          // under the new path, remove the file with the same name as the
          // oldpath object
        } else {
          Parent_olddir.mvChildFile(oldfile.getFileName());
          newdir.mvChildFile(oldfile.getFileName());
        }

        // add the old directory under the new directory
        if (olddir.getDirectoryName().length() > 0) {

          newdir.addChildDirectory(olddir);
          // else add the old file under the new directory
        } else {
          newdir.addChildFile(oldfile);
        }
        // else throw error message
      } else {
        throw new InvalidPathException("mv: cannot move '" + oldpathname
            + "' to '" + newpathname + "': No such file or directory");
      }

    } catch (InvalidSyntaxException | InvalidPathException e) {
      output = e.getMessage();
    } catch (Exception e) {
      output = e.getMessage();
    }

    return output;
  }

  /**
   * This method check if the mv arguments are valid or not
   * 
   * @throws InvalidSyntaxException
   */
  public List<String> argumentCheck(List<String> argsList, Shell shell)
      throws InvalidSyntaxException {
    // Check if the mv parameters have valid syntax
    if (argsList.size() != 2) {
      throw new InvalidSyntaxException("Invalid Syntax for the Mv command");
    }

    List<String> newArgsList = new ArrayList<String>();

    for (String Args : argsList) {
      newArgsList.add(Args.trim());
    }

    if (argsList.get(0).equals(argsList.get(1))) {
      throw new InvalidSyntaxException(
          "mv: ‘" + argsList.get(0) + "’ and " + "‘" + argsList.get(1)
              + "’ are the same file OR  directory ‘" + argsList.get(0) + "’");
    }
    return newArgsList;
  }

}
