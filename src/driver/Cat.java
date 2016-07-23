/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author osaru
 *
 */
public class Cat implements Command {

  /**
   * This method executes the Cat command, to return the Files contents
   */

  private boolean outFilePresent = false; // stores whether or not an outFile
                                          // has been passed in.

  @Override
  public String execute(List<String> argsList, Shell shell)
      throws InvalidFileNameException, InvalidSyntaxException {
    String output = "";
    // check if the arguments passed in were valid or not
    if (argumentChecker(argsList)) {
      // if the user wants the output redirected to an outFile, then do so
      if (outFilePresent) {
        // retrieve the contents of the files, passing in only the existing
        // files
        // and not the redirection parameters
        output =
            retrieveContents(argsList.subList(0, argsList.size() - 2), shell);
        // Strip off the extra new line characters before writing to file
        output = "\"" + output + "\"";
        List<String> echoArgs;
        echoArgs = Arrays.asList(output, argsList.get(argsList.size() - 2),
            argsList.get(argsList.size() - 1));
        Echo writer = new Echo();
        output = writer.execute(echoArgs, shell);
      } else {
        output = retrieveContents(argsList, shell);
      }
    } else {
      throw new InvalidSyntaxException(
          "This syntax is Invalid for the Cat" + "command");
    }
    outFilePresent = false;
    return output;
  }

  /**
   * This method checks whether the arguments passed in are valid.
   * 
   * @param The list of arguments to be checked
   */
  public boolean argumentChecker(List<String> argsList) {
    boolean valid = true;
    // check if there is a redirect symbol
    for (int i = 0; i < argsList.size(); i++) {
      if (argsList.get(i).equals(">") || argsList.get(i).equals(">>")) {
        outFilePresent = true;
        // This must be at the second to the last position for it to be valid
        valid = (i == argsList.size() - 2);
      }
    }
    return valid;
  }

  /**
   * @param argsList the list of files to get their contents
   * @param shell the current shell we are working with
   * @return the contents retrieved from the given files
   * @throws InvalidFileNameException
   */
  public String retrieveContents(List<String> argsList, Shell shell)
      throws InvalidFileNameException {
    String output = "";
    // Get The shell's current directory to work with
    Directory currentDir = shell.getCurrentDirectory();
    // loop through the list of file names
    for (String name : argsList) {
      // check if the file name is a path
      if (name.length() > 0) {
        if (name.charAt(0) == '/') {
          // check if the file exists if it does print its contents on
          // Shell
          if (currentDir.isChildPath(name, "file")) {
            // print the file's contents to the Shell
            File file = currentDir.getPathFile(name);
            output += file.printContent();
            // if it doesn't exist, give an error message
          } else {
            handleError(output, name);
          }
        }
        // if we're given a file name and not a path...
        else {
          // Check for the file's existence and print its contents if
          // it exists
          if (currentDir.isChildPath(name, "file")) {
            File file = currentDir.getPathFile(name);
            output += file.printContent();
            // if it doesn't exist, give an error message
          } else {
            output = handleError(output, name);
          }
        }
      }
    }
    return output;
  }

  /**
   * This method handles errors accordingly, printing an error message or
   * appending it to the output depending on whether or not redirection is
   * required
   * 
   * @param output
   * @return
   */
  private String handleError(String output, String name) {
    String errorMessage =
        "The file name " + name + " is invalid please" + " try another\n\n\n";
    // if output is to be redirected to a file don't add the error
    // message but simply print it
    if (outFilePresent) {
      System.out.println(errorMessage);
      return output;
    } else {
      // if not return it with the output to Shell.
      return output += errorMessage;
    }
  }

}
