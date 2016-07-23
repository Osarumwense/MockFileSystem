
package driver;

import java.util.HashMap;
import java.util.List;

/**
 * A basic class to represent the Manual command
 * 
 * @author Jason Ku
 *
 */
public class Man implements Command {
  private HashMap<String, String> commandsAndDoc;

  /**
   * Default constructor to initialize all the manual info for each command
   * 
   */
  public Man() {
    // Create and populate commandsAndDoc with correct documentation for
    // commands
    commandsAndDoc = new HashMap<String, String>();
    // Populate documentation for the exit command
    // For exit command
    commandsAndDoc.put("exit",
        "NAME: exit -- Terminates and closes the "
            + "shell\n\nSYNOPSIS: exit\n\nDESCRIPTION: The exit command "
            + "will terminate and exit the program when input into the"
            + " shell.\n\nEXAMPLE: The command:\n             | exit\n    "
            + "     will exit the program and shell.");
    // For cp command
    commandsAndDoc.put("cp",
        "NAME: cp -- Moves a file or directory to a new location"
            + " without removing the original "
            + "location\n\nSYNOPSIS: cp [OLDPATH] [NEWPATH]\n\nDESCRIPTION: "
            + "The cp command will move a file or directory from [OLDPATH] to "
            + "[NEWPATH] without removing the original\n             "
            + "file/directory in "
            + "[OLDPATH]\n\nEXAMPLE: The command:\n             | "
            + "cp /a/ /b/c/\n         will copy all files in the directory "
            + "/a/ and move them to the new path /b/c/, while keeping structure"
            + " in both the \n"
            + "         new location and the old location.");
    // For curl command
    commandsAndDoc.put("curl",
        "NAME:  Retrieve the file at that URL and add it's contents to a "
            + "file\n\nSYNOPSIS: curl [URL]\n\nDESCRIPTION: The curl command will"
            + " retrieve a URL and get the URL's contents and add it to a file in"
            + " the current\n             working directory with the same name as "
            + "the file on the URL.\n\nEXAMPLE: The command:\n             | curl"
            + " http://www.cs.cmu.edu/~spok/grimmtmp/073.txt\n         Will get"
            + " the contents of the file i.e. 073.txt and create a file called\n"
            + "         073.txt with the contents in the current working "
            + "directory.");
    // For grep command
    commandsAndDoc.put("grep",
        "NAME: Gets all lines in all files that contain "
            + "REGEX and print the path to the file (including the filename),\n"
            + "      then a colon, then the line that contained REGEX\n\n"
            + "SYNOPSIS: grep [-R] [REGEX] [PATH...]\n\nDESCRIPTION: The grep "
            + "command will search the given REGEX in the given PATH (if given)"
            + " and print the path to the file \n             and the"
            + " line containing the "
            + "regex. If -R is not supplied, then print any lines containing "
            + "REGEX in [PATH]\n\nEXAMPLE: The command:\n             | grep 0*"
            + " /a\nWill print any lines in files in /A containing the REGEX"
            + " 0*");
    // For mv command
    commandsAndDoc.put("mv",
        "NAME: mv -- Moves a file or directory to a new location"
            + "\n\nSYNOPSIS: mv [OLDPATH] [NEWPATH]\n\nDESCRIPTION: "
            + "The mv command will move a file or directory from [OLDPATH] to "
            + "[NEWPATH]\n\nEXAMPLE: The command:\n             | "
            + "mv /a/ /b/c/\n         will move all files in the directory "
            + "/a/ and move them to the new path /b/c/");
    // For mkdir command
    commandsAndDoc.put("mkdir",
        "NAME: mkdir -- Make directories "
            + "on the file system\n\nSYNOPSIS: mkdir [DirectoryName] "
            + "[path...]\n\nDESCRIPTION: The mkdir command will create a "
            + "directory (or several) in the current\n             "
            + "directory if a path is not given, otherwise, it will create "
            + "directories\n             in the given path. Directory "
            + "names must follow the naming convention\n             "
            + "specified on the Assignment 2A handout. \n\nEXAMPLE: "
            + "The command:\n             | mkdir directory1\n         will"
            + "  create a directory called directory1 in the current "
            + "directory\n\n         The command:\n             | mkdir "
            + "directory2 /root/test/b07/ \n         will create a "
            + "directory called directory2 in the path /root/test/b07/ \n"
            + "        given that the path exists\n\n         The "
            + "command:\n             | mkdir directory3 directory4\n     "
            + "    will create two directories called directory3 and "
            + "directory4 in the current\n         directory");
    // For cd command
    commandsAndDoc.put("cd",
        "NAME: cd -- Change the current "
            + "working directory to the given path, either\n              "
            + "     relative to the current directory or a full path on "
            + "the file system\n\nSYNOPSIS: cd [path...]\n\nDESCRIPTION: "
            + "The cd command will change the current working directory to "
            + "the given path.\n             If there is no full path "
            + "given, then the command will assume there is a\n           "
            + "  directory existing under the current working directory."
            + "\n\nEXAMPLE: The command:\n             | cd directory1\n   "
            + "      will change the current working directory into a "
            + "directory called directory1 \n         that is in the "
            + "current working directory\n\n         The command:\n       "
            + "      | cd directory2 /root/test/b07/ \n         will "
            + "change the current working directory into a directory "
            + "called directory2 \n         in the path /root/test/b07/, "
            + "given that the path exists");
    // For ls command
    commandsAndDoc.put("ls",
        "NAME: ls -- Display all of the "
            + "contents in a directory if location of a\n                 "
            + "     directory is given and print the file if it is a file "
            + "location\n\nSYNOPSIS: ls [-R] [path...]\n\nDESCRIPTION: The ls "
            + "command will print the current working directory and its "
            + "contents\n             if path... is a directory or if "
            + "path... is not given. If path... is a file,\n           "
            + "  print the file. If path... is not valid, then print an "
            + "error message.\n\nEXAMPLE: The command:\n             |"
            + " ls\n         will print the current working directory\'s "
            + "contents\n\n         The command:\n             | ls "
            + "/root/test/b07/ \n         will print the contents of the "
            + "directory /root/test/b07/, given that the\n        "
            + " location exists\n\n         The command:\n            "
            + " | ls someFile.txt\n         will print someFile.txt");
    // For pwd command
    commandsAndDoc.put("pwd",
        "NAME: pwd -- Print the location of the "
            + "current working directory relative to the file\n           "
            + "  system\n\nSYNOPSIS: pwd\n\nDESCRIPTION: The pwd command "
            + "will print the location of the current working directory"
            + "\n\nEXAMPLE: The command:\n             pwd\n         will "
            + "print the current working directory\'s location on the file"
            + " system");
    // For pushd command
    commandsAndDoc.put("pushd",
        "NAME: pushd -- Save the location of the "
            + "current directory and change the current working directory "
            + "to [path...]\n\nSYNOPSIS: pushd [path...]\n\nDESCRIPTION: "
            + "The pushd command will Save the location of the current "
            + "working directory\n             on a stack and then change "
            + "the current working directory to [path...], as\n           "
            + "  long as [path...] exists and is a valid directory on the"
            + " file system.\n\nEXAMPLE: The command:\n           "
            + "  pushd /root/test/b07/ \n         will save the current"
            + " working directory\'s location on the file system onto a\n "
            + "        stack. Then, the current working directory will be "
            + "changed to the location of\n         /root/test/b07/");
    // For popd command
    commandsAndDoc.put("popd",
        "NAME: popd -- Get the next directory saved"
            + " on the stack and change the current working directory "
            + "into\n              it.\n\nSYNOPSIS: popd\n\nDESCRIPTION: "
            + "The popd command retrieves the next directory location on "
            + "the stack (LIFO)\n             and changes the current "
            + "working directory into the location of the retrieved\n    "
            + "         directory. If there is no directory on the stack,"
            + " then an error message\n             will be printed\n\n"
            + "EXAMPLE: The command\n             | popd\n         will "
            + "change the current working directory into the directory "
            + "retrieved by\n         the popd command");
    // For history command
    commandsAndDoc.put("history",
        "NAME: history -- Print all of the last "
            + "inputs given by the user\n\nSYNOPSIS: history [number]\n\n"
            + "DESCRIPTION: The history command retrieves the last [number]"
            + " of inputs given\n\nEXAMPLE: The command:\n             "
            + "| history\n         will print out all of the previous given"
            + " inputs by the user in order of oldest\n         "
            + "to most recent\n\n         The command:\n             | "
            + "history 4\n         will print out the last 4 inputs given"
            + " by the user from oldest to most recent");
    // For cat command
    commandsAndDoc.put("cat",
        "NAME: cat -- Print the contents of a file "
            + "to display in the shell\n\nSYNOPSIS: cat [FILE1 PATH...] "
            + "[FILE2 PATH...] ...\n\nDESCRIPTION: The cat command will "
            + "display the contents of a file (or several) given the\n   "
            + "          location of the files in the shell. If the "
            + "location is invalid, an error \n             message will"
            + " be printed.\n\nEXAMPLE: The command:\n             | cat"
            + " file1.txt\n         will print out the contents of the "
            + "file file1.txt in the shell, given that\n         file1.txt"
            + " exists in the current working directory\n\n         "
            + "The command:\n             | cat /root/test/b07/file2.txt "
            + "/root/file3.txt\n         will print out the contents of "
            + "the file file2.txt residing in the /root/test/b07\n     "
            + "    directory. It will then print out the contents of "
            + "file3.txt residing in the\n         /root/ directory");
    // For echo command
    commandsAndDoc.put("echo",
        "NAME: echo -- Append, overwrite, or create "
            + "a new file with contents passed in\n\nSYNOPSIS: echo "
            + "[CONTENTS] [> or >>] [OUTPUTFILE PATH...]\n\nDESCRIPTION:"
            + " The echo command will append to a current file "
            + "[OUTPUTFILE PATH...] if the\n             \">>\" symbol is"
            + " passed in. It will overwrite [OUTPUTFILE PATH...] if the\n"
            + "             \">\" symbol is passed in. If there is no "
            + "symbol, then the [CONTENTS] will\n             be displayed"
            + " in the shell.\n\nEXAMPLE: The command:\n             | "
            + "echo \"file1 contents\" > file1.txt\n         will create a"
            + " new file called file1.txt with contents \"file1 contents\""
            + " in the\n         current working directory or overwrite the"
            + " file if it already exists\n\n         The command:\n      "
            + "       | echo \"file2 contents\" >> file2.txt\n         will"
            + " append the string \"file2 contents\" to a file called "
            + "file2.txt in the\n         current working directory\n\n "
            + "        The command:\n             | echo \"file3 contents\""
            + " > /root/b07/test/file3.txt will create a new file\n  "
            + "       called file3.txt with contents \"file3 contents\" "
            + "in the directory /root/b07/test/\n         or overwrite"
            + " the file if it already exists. If no directory exists, "
            + "an error \n         message is printed");
    // For man command
    commandsAndDoc.put("man",
        "NAME: man -- Print out the documentation of"
            + " the given command\n\nSYNOPSIS: man [COMMAND]\n\n"
            + "DESCRIPTION: The man command will print out the "
            + "documentation of a given [COMMAND]\n\nEXAMPLE: The "
            + "command:\n             | man cat\n         will print out"
            + " the documentation for the cat command\n\n         The"
            + " command:\n             | man man\n         will print out"
            + " this exact documentation as seen right now.");
    // For number command
    commandsAndDoc.put("!",
        "NAME: ! -- Get the n'th input given where n is a"
            + " int\n\nSYNOPSIS: ![NUMBER]\n\nDESCRIPTION: The ! command gets"
            + " the n'th number input \n\nEXAMPLE: The command:\n          "
            + "   | !2\n         will get the second input given to the shell");
  }

  /**
   * Get the documentation of a certain command
   * 
   * @param command The command you want to retrieve
   * @return ArrayList<String> The documentation that corresponds to the command
   * @throws InvalidSyntaxException
   */
  public String getDoc(String command) throws InvalidSyntaxException {
    String message;
    // Try and retrieve the documentation for the command
    String toPrint = commandsAndDoc.get(command);
    // If the command exists, then print the documentation for it
    if (toPrint != null) {
      // Print the documentation for the command
      message = toPrint;
    } else {
      // Print an error message
      throw new InvalidSyntaxException(command + " is not a supported command");
    }
    return message;
  }

  /**
   * This command executes the Man command when called by teh user
   * 
   * @throws InvalidSyntaxException
   */
  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidSyntaxException {
    String doc = getDoc(args.get(0));
    return doc;
  }

  /**
   * This method returns the manual for the given command
   * 
   * @param key the command you want to get the manual for
   * @return the manual for key
   */
  public String getCommandsAndDoc(String key) {
    return commandsAndDoc.get(key);
  }

}
