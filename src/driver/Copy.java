/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ruyinzhang
 *
 */
public class Copy implements Command {

  /**
   * This method executes the cp command as requested by the user
   * 
   * @throws InvalidSyntaxException
   */
  @Override
  public String execute(List<String> argsList, Shell shell)
      throws InvalidSyntaxException {
    String output = " ";
    // Initialized the validPath as False
    boolean validPath = false;
    // create an empty directory object to record the newPath objetc later
    Directory newdir = new Directory("");
    // create an empty directory object to record the oldPath object later
    Directory olddir = new Directory("");
    // create an empty directory object to record the new copy object
    Directory copy_olddir = new Directory("");
    // create a directory object to record the oldPath's parent directory later
    Directory ParentOlddir;
    // create a File object to record the oldPath file later
    File oldfile = new File("", "");
    // create a File obejct to record the new copy of the file object
    File copy_oldfile = new File("", "");
    // create an empty string type object to record the directory path
    String directoryPath;
    // create an empty string object to record the new file/directory name
    String newname;
    // create an empty string object to record the old file/directory name
    String oldname;

    try {
      // First check if the syntax entered is correct
      argsList = argumentCheck(argsList, shell);
      // record the name of the oldpath and newpath
      String oldpath = argsList.get(0);
      String oldpathname = argsList.get(0);
      String newpath = argsList.get(1);
      String newpathname = argsList.get(1);

      // convert the relative paths to the absolute paths
      if (oldpath.charAt(0) != ('/')) {
        oldpath = shell.getFullPath() + oldpath;
      }

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
      ParentOlddir =
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
            "cp: cannot stat '" + oldpathname + "': No such file or directory");
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
        }
        // if olddir is a directory
        if (olddir.getDirectoryName().length() > 0) {
          // if the newpath we are going to add is a directory
          if (shell.getRootDirectory().isChildPath(newpath.substring(1),
              "directory")) {
            // set the copy directory's name as the old diretory's name
            copy_olddir.setDirectoryName(oldname);
            // else the newpath is not a directory, throw exception error
            // because we do not accept the following format: cp directory file
          } else {
            throw new InvalidPathException(
                "cp: omitting directory '" + oldpathname + "'");
          }

          // copy all the directories under the old directory to a new copy
          // object
          for (Directory nextdir : olddir.getChildrenDirectories()) {
            copy_olddir.addChildDirectory(nextdir);
          }
          // copy all the files under the old directory to a new copy object
          for (File nextfile : olddir.getChildFiles()) {
            copy_olddir.addChildFile(nextfile);
          }
          // delete the same directory name under the newdir
          newdir.mvChildDirectory(olddir.getDirectoryName());
          // add the new copy object to the new directory
          newdir.addChildDirectory(copy_olddir);
          // else the olddir is a file
        } else {
          // if the newpath we are going to add is a directory
          if (shell.getRootDirectory().isChildPath(newpath.substring(1),
              "directory")) {
            // set the name of the new copy file object as the same as the old
            // file's name
            copy_oldfile.setFileName(oldname);
            // remove the file object in the new directory which contains the
            // same file name
            newdir.mvChildFile(oldfile.getFileName());
            // else the newpath we are going to add is a file as well
          } else {
            // set the name of the new copy file as the same as the old file's
            // name
            copy_oldfile.setFileName(newname);
            // under the new directory, remove the file object with the same
            // name as the copy object's
            newdir.mvChildFile(newname);
          }
          // copy the file content from the oldfile to the copy file object
          copy_oldfile.setFileContent(oldfile.getFileContent());
          // add the copy file object under the new directory
          newdir.addChildFile(copy_oldfile);
        }
        // else the new path is not an existence path
      } else {
        throw new InvalidPathException("mv: cannot move '" + oldpathname
            + "' to '" + newpathname + "': No such file or directory");
      }
      // catch invalid syntax exception or invalid path exception
    } catch (InvalidSyntaxException | InvalidPathException e) {
      output = e.getMessage();
    } catch (Exception e) {
      output = e.getMessage();

    }

    return output;
  }


  /**
   * This method check if the cp arguments are valid or not
   * 
   * @throws InvalidSyntaxException
   */
  public List<String> argumentCheck(List<String> argsList, Shell shell)
      throws InvalidSyntaxException {
    // Check if the cp parameters have valid syntax
    if (argsList.size() != 2) {
      throw new InvalidSyntaxException("Invalid Syntax for the cp command");
    }

    List<String> newArgsList = new ArrayList<String>();
    // get rid of the leading and trailing white spaces
    for (String Args : argsList) {
      newArgsList.add(Args.trim());
    }
    // if the old path and new path is the same path, throw exception error
    if (argsList.get(0).equals(argsList.get(1))) {
      throw new InvalidSyntaxException(
          "cp: ‘" + argsList.get(0) + "’ and" + " ‘" + argsList.get(1)
              + "’ are the same file OR omitting directory ‘" + argsList.get(0)
              + "’");
    }
    return newArgsList;
  }
}
