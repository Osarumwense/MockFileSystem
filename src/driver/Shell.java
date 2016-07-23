package driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the main shell for the mock file system
 * 
 * @author Jason
 *
 */
public class Shell {

  private Boolean status;
  private ArrayList<String> historyList;
  private Scanner input;
  private Interpreter interpreter;
  private Directory rootDirectory;
  private Directory currDirectory;
  private String fullPath;
  private DirectoryStack dirStack;

  /**
   * Constructor for a Shell by initiating the status of the shell to true
   */
  public Shell() {
    // Create the status of the shell (ie, turn the shell on)
    status = true;
    // Create the history list
    historyList = new ArrayList<String>();
    // Create the scanner to read commands given by the user
    input = new Scanner(System.in);
    // Create the interpreter to decipher commands
    interpreter = new Interpreter();
    // Create the curr and root directory
    rootDirectory = new Directory("/");
    rootDirectory.setParent(null);
    currDirectory = rootDirectory;
    // Create a directoryStack object
    dirStack = new DirectoryStack();
    // Create a String that holds the full path of the current directory
    fullPath = "/";
  }

  /**
   * Display the Shell by requesting for a command from the user.
   * 
   * 
   */
  public void display() {
    // Create a string to hold the command of the user
    String userInput;
    List<String> arguments;
    Command command;
    System.out.println(this.fullPath + " Please enter a command: ");
    // Retrieve the user's input
    userInput = input.nextLine();
    // Check if the command is exit, if it is, terminate
    if (userInput.trim().equals("exit")) {
      this.terminate();
    } else {
      try {
        // Request the translated command from the interpreter
        interpreter.interpret(userInput);
        command = interpreter.getCommand();
        arguments = interpreter.getArguments();
        // Check if arguments were passed in
        if (arguments.size() == 0 && !(LsCommand.class.isInstance(command)
            || Popd.class.isInstance(command)
            || PrintWorkingDirectory.class.isInstance(command)
            || History.class.isInstance(command))) {
          throw new InvalidSyntaxException("No arguments were passed"
              + " in, please pass in valid arguments to the command.");
        }
        // Check if redirection was requested
        if ((arguments.size() > 1
            && (arguments.get(arguments.size() - 2).equals(">")
                || arguments.get(arguments.size() - 2).equals(">>"))
            && !(command.getClass().getName().equals("driver.Echo")))) {
          // Run the command with the redirection arguments stripped
          ArrayList<String> newArgs = new ArrayList<String>();
          int loopInt = 0;
          while (loopInt < arguments.size() - 2) {
            newArgs.add(arguments.get(loopInt));
            loopInt++;
          }
          String output = command.execute(newArgs, this);
          // Call echo on the command
          Interpreter forEcho = new Interpreter();
          forEcho.interpret("echo");
          Command echo = forEcho.getCommand();
          // Create an arraylist for echo
          ArrayList<String> echoList = new ArrayList<String>();
          // Add the output
          echoList.add("\"" + output + "\"");
          // Add the > or >> redirection type
          echoList.add(arguments.get(arguments.size() - 2));
          // Add the file name
          echoList.add(arguments.get(arguments.size() - 1));
          // Call echo on the output and file
          echo.execute(echoList, this);
        } else {
          String output = command.execute(arguments, this);
          System.out.println(output);
        }
      } catch (InvalidCommandException | InvalidPathException
          | InvalidSyntaxException | NoParentException
          | InvalidFileNameException | InvalidHistoryNumberException
          | IOException ex) {
        System.out.println(ex.getMessage());
      }
    }
    // Add the command to history
    historyList.add(userInput);
  }

  /**
   * A method to return the interpreter object
   * 
   * @return interpreter The interpreter object of Shell
   */
  public Interpreter getInterpreter() {
    return interpreter;
  }

  /**
   * A getter method to get the status of the Shell
   * 
   * @return status The current state of the Shell (on/off)
   */
  public Boolean getStatus() {
    // Return the status of the shell
    return this.status;
  }

  /**
   * Set the status of the Shell to off and terminate
   */
  private void terminate() {
    // Set status to false
    this.status = false;
    // Close the input scanner
    input.close();
  }

  /**
   * This method returns the current directory the shell is in
   * 
   * @return the current directory the shell is in
   */
  public Directory getCurrentDirectory() {
    return currDirectory;
  }

  /**
   * Change the current directory of the shell
   */
  public void setCurrentDirectory(Directory newCurrent) {
    this.currDirectory = newCurrent;
  }

  /**
   * This method returns the root directory the shell is in
   * 
   * @return the root directory the shell is in
   */
  public Directory getRootDirectory() {
    return rootDirectory;
  }

  /**
   * A method to get the full path of the current directory
   * 
   * @return fullPath The full path
   */
  public String getFullPath() {
    return fullPath;
  }

  /**
   * Change the fullPath
   * 
   * @param fullPath The new fullPath string
   */
  public void changeFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  /**
   * Get method for the directory stack
   * 
   * @return dirStack The directory stack object
   */
  public DirectoryStack getDirStack() {
    return dirStack;
  }

  /**
   * Getter that returns the list of all entered commands, used by History class
   * 
   * @return The list of commands entered by the user
   */
  public ArrayList<String> getHistoryList() {
    return historyList;
  }

}
