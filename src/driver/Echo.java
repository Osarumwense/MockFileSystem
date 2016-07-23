/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent the Echo command used to write to or create Files
 * 
 * @author Osaru
 */
public class Echo implements Command {

  /**
   * This method executes the echo command as requested by the user
   * 
   * @throws InvalidSyntaxException
   */
  @Override
  public String execute(List<String> argsList, Shell shell)
      throws InvalidSyntaxException {
    String output = "";
    // First check if the syntax entered is correct
    try {
      argsList = argumentCheck(argsList, shell);
      // Get the current and root Directories to work with
      Directory currentDir = shell.getCurrentDirectory();
      Directory rootDir = shell.getRootDirectory();
      // if there is no OUTFILE, print the output on the shell
      int size = argsList.size();
      String inputString = argsList.get(0);
      if (size == 1) {
        output = argsList.get(0);
      }
      // if there is an OUTFILE, then first check for its existence
      else if (size == 3) {
        String outFile = argsList.get(2);
        // check first if you have an absolute path
        if (outFile.charAt(0) == ('/')) {
          // check if the path is valid, (it can't end with a /)
          if (!(outFile.charAt(outFile.length() - 1) == '/')) {
            // strip off the first "/" if the root directory path wasn't passed
            // in
            if (outFile.length() > 1) {
              outFile = outFile.substring(1);
            }
            // check if the file exists then append or overwrite if it does
            if (rootDir.isChildPath(outFile, "file")) {
              File file = rootDir.getPathFile(outFile);
              output = file.edit(inputString, argsList.get(1));
            }
            // if it doesn't exist create a new one
            else {
              // find the name of the file in the path
              int fileStart = outFile.lastIndexOf('/', outFile.length() - 1);
              String name = outFile.substring(fileStart + 1);
              // make sure there isn't an already existing Directory in the
              // target directory for this file
              String directoryPath = outFile.substring(0, fileStart);
              Directory dir = rootDir.getPathDirectory(directoryPath);
              if (dir.dirExists(name)) {
                output = "Error! There is already an existing directory with"
                    + " this name. Please try another name.";
              } else {
                // Create a new file object and add it to the specified
                // directory
                File file = new File(name, inputString);
                dir.addChildFile(file);
                output = "New File successfully created.";
              }
            }
          } else {
            throw new InvalidSyntaxException(
                "Error! File paths can't" + "end with '/'");
          }
        }
        // if its not an absolute path then check if the file exists in the
        // relative path
        else {
          // first check if the path was valid (file paths can't end with '/')
          if (!(outFile.charAt(outFile.length() - 1) == '/')) {
            // if it exists just append or overwrite it
            if (currentDir.isChildPath(outFile, "file")) {
              File file = currentDir.getPathFile(outFile);
              output = file.edit(inputString, argsList.get(1));
            } else {
              // if it doesn't create a new file with inputString as its
              // contents, first get the right target directory
              Directory dir;
              int fileStart = outFile.lastIndexOf('/', outFile.length() - 1);
              // find the name of the file in the path
              String name = outFile.substring(fileStart + 1);
              if (outFile.contains("/")) {
                String directoryPath = outFile.substring(0, fileStart);
                dir = currentDir.getPathDirectory(directoryPath);
              } else {
                dir = currentDir;
              }
              if (dir.dirExists(name)) {
                throw new InvalidFileNameException("Error! There is already"
                    + " an existing directory with this name. Please try another.");
              }
              File file = new File(name, inputString);
              output = "New File successfully created.";
              dir.addChildFile(file);
            }
          } else {
            throw new InvalidSyntaxException(
                "Error! File paths can't" + "end with '/'");
          }
        }
      }
    } catch (InvalidSyntaxException e) {
      output = e.getMessage();
    } catch (Exception e) {
      output = e.getMessage();
    }
    return output;
  }

  public List<String> argumentCheck(List<String> argsList, Shell shell)
      throws InvalidSyntaxException {
    // Check if the echo parameters have valid syntax
    List<String> newArgsList = new ArrayList<String>();

    String input = "";
    int i = 0;
    String param = argsList.get(i);
    input = param + " ";
    i++;
    // loop through the list of parameters to combine the user's String
    while ((param.charAt(param.length() - 1) != '"') && i < argsList.size()) {
      param = argsList.get(i);
      input += param + " ";
      i++;
    }
    // Strip the last space;
    input = input.substring(0, input.length() - 1);
    newArgsList.add(input);
    newArgsList.addAll(argsList.subList(i, argsList.size()));

    // if there is more than one parameter passed in, then there must be a
    // redirection arrow as well, or else it is invalid.
    if (newArgsList.size() > 1) {
      // check for the arrow, which should be the second to the last item
      if (!(newArgsList.get(newArgsList.size() - 2).equals(">>")
          || newArgsList.get(newArgsList.size() - 2).equals(">"))) {
        throw new InvalidSyntaxException(
            "Error! Missing redirection arrows" + "(>/>>)");
      }
    }

    // Check if the string entered starts with quotes to maintain syntax
    String word = newArgsList.get(0);
    if (!((word.charAt(0) == '"')
        && (word.indexOf('"', 1) == word.length() - 1))) {
      throw new InvalidSyntaxException("Invalid Syntax for the Echo command");
    }
    // if it does remove the quotes for proper format to be written to file
    else {
      newArgsList.remove(0);
      word = word.substring(1, word.length() - 1);
      newArgsList.add(0, word);
    }
    return newArgsList;
  }


}
