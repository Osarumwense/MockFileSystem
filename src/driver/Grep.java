/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to represent the Grep command and support its functionalities
 * 
 * @author osaru
 */
public class Grep implements Command {

  private boolean recursive; // a variable to store whether or the -R label has
                             // been passed in.
  private String regex; // The regex to be matched
  private Pattern regexPattern; // The regex pattern to be matched.

  /**
   * The method that is called for the Grep command to execute.
   * 
   * @param args the list of arguments passed in by the user
   * @param shell the current Shell being in use.
   * @throws InvalidSyntaxException is thrown if the argument list is invalid
   */
  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidSyntaxException {
    String output = "";
    Directory root = shell.getRootDirectory();
    Directory currDir = shell.getCurrentDirectory();
    Directory searchDir;
    List<String> pathList;
    // check if the user asked for a Recursive Grep
    if (args.size() >= 2) {
      recursive = args.get(0).equalsIgnoreCase("-R");
      if (recursive) {
        regex = args.get(1);
        // get the list of files and directories to be Greped
        pathList = args.subList(2, args.size());
      }
      // if not then our regex pattern should be at the first index
      else {
        regex = args.get(0);
        // get the list of files and directories to be Greped
        pathList = args.subList(1, args.size());
      }
      // check for proper syntax
      if (!((regex.charAt(0) == '"')
          && (regex.indexOf('"', 1) == regex.length() - 1))) {
        throw new InvalidSyntaxException(
            "Invalid Syntax for the Grep" + "command");
      }
      // remove the quotes for proper regex format
      regex = regex.substring(1, regex.length() - 1);
      // get the pattern to be matched from the regex given
      regexPattern = Pattern.compile(regex);
    } else {
      throw new InvalidSyntaxException(
          "Error! Grep takes at least 2" + " parameters");
    }

    // loop through the list of paths given in the arguments
    for (String path : pathList) {
      // check if the path is relative or absolute
      // if absolute strip off the first "/" and proceed
      if (path.startsWith("/")) {
        // do this only if the root name (/) wasn't passed in
        if (path.length() > 1) {
          path = path.substring(1);
        }
        searchDir = root;
      } else {
        searchDir = currDir;
      }
      // if its recursive check if its a directory then proceed
      if (recursive) {
        if (searchDir.isChildPath(path, "directory")) {
          // get the Directory then grep on the directory
          Directory dir = searchDir.getPathDirectory(path);
          output += grepDir(dir);
        } else {
          System.out.println(path + ": Error! not a valid directory");
        }
      } else {
        // if its not recursive, check if its a file then proceed.
        if (searchDir.isChildPath(path, "file")) {
          // Get the File object then grep on the file
          File file = searchDir.getPathFile(path);
          output += grepFile(file, searchDir);
        } else {
          System.out.println(path + ": Error, -R not a valid File");
        }
      }
    }
    return output;
  }

  /**
   * This method checks goes through the given file, and for each line in the
   * file, checks if there is a word that matches regex.
   * 
   * @param file the file to get the matching contents from
   * @param dir The directory where this file is located.
   * @return Lines of the file that contain Strings that match the given REGEX
   */
  public String grepFile(File file, Directory dir) {
    String output = "";
    String fileName = file.getFileName();
    // get a list of all the lines in the file
    String contents = file.getFileContent();
    String[] lines = contents.split("\n");
    // loop through the lines and check if a word matches the regex
    for (String line : lines) {
      // create a matcher object to match the regex with the line
      Matcher regexMatcher = regexPattern.matcher(line);
      // return the line if it matches the regex
      if (regexMatcher.find()) {
        output += dir.getFullPath() + "/" + fileName + ": " + line + "\n";
      }
    }
    return output;
  }

  /**
   * This method is a helper function for execute which deals with
   * subdirectories
   * 
   * @param dir The directory that we want to call Grep on.
   * @return the lines in the subdirectories and files that match regex
   */
  public String grepDir(Directory dir) {
    String output = "";
    // get the list of all the subdirectories in the directory and loop through
    // them, calling grep on each one.
    ArrayList<Directory> subDirList = dir.getChildrenDirectories();
    for (Directory subDir : subDirList) {
      output += grepDir(subDir);
    }
    // do the same for the Files
    ArrayList<File> fileList = dir.getChildFiles();
    for (File file : fileList) {
      output += grepFile(file, dir);
    }
    return output;
  }

}
