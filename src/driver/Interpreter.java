package driver;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to represent an Interpreter to parse through the userInput
 * 
 * @author osaru
 *
 */
public class Interpreter {

  private Set<String> commands = new HashSet<String>(
      Arrays.asList("mkdir", "cd", "ls", "pwd", "pushd", "popd", "history",
          "cat", "echo", "man", "curl", "mv", "cp", "!", "grep"));

  private ArrayList<String> commandsAndArgs; // holds all commands and arguments
  private Hashtable<String, Command> commandTable =
      new Hashtable<String, Command>(14); // holds valid command names

  /**
   * A constructor that initializes the parsed input to an empty Array
   */
  public Interpreter() {
    commandTable.put("mkdir", new MakeDirectory());
    commandTable.put("cd", new ChangeDirectory());
    commandTable.put("ls", new LsCommand());
    commandTable.put("pwd", new PrintWorkingDirectory());
    commandTable.put("pushd", new Pushd());
    commandTable.put("popd", new Popd());
    commandTable.put("history", new History());
    commandTable.put("cat", new Cat());
    commandTable.put("echo", new Echo());
    commandTable.put("man", new Man());
    commandTable.put("curl", new Curl());
    commandTable.put("!", new Number());
    commandTable.put("mv", new Move());
    commandTable.put("cp", new Copy());
    commandTable.put("grep", new Grep());
  }

  /**
   * This method parses through the User's Input to separate the command and the
   * arguments
   * 
   * @param user_input The input String entered by the User
   * @return An ArrayList of a command and its arguments with the command in the
   *         first index.
   */
  public void interpret(String user_input) throws InvalidCommandException {
    commandsAndArgs = new ArrayList<String>();
    // Get the list of commands and arguments
    String[] tempList = user_input.split("\\s");
    // Copy these items into an extensible List
    for (String param : tempList) {
      // do not add empty strings
      if (param.length() > 0) {
        commandsAndArgs.add(param);
      }
    }
    // Check if the command (the first item in the list) is valid, if present
    if (commandsAndArgs.size() > 0) {
      // if it's the ! command separate it from the number attached.
      if (commandsAndArgs.get(0).startsWith("!")) {
        // Get the number parameter from the original string
        String oldJointParam = commandsAndArgs.get(0);
        String numberParam = oldJointParam.replaceFirst("!", "");
        // now change the original parameter List to contain
        // the two separate parameters
        commandsAndArgs.add("!");
        commandsAndArgs.add(numberParam);
        commandsAndArgs.remove(0);
      }
      // if it's invalid print an error message to inform the user
      if (!(commands.contains(commandsAndArgs.get(0)))) {
        throw new InvalidCommandException("This is an invalid command");
      }
    }
  }

  /**
   * This method returns the valid command the user entered
   * 
   * @return the command Object
   */
  public Command getCommand() {
    // the name of the command is in the first index of the
    // CommandsAndArgsList
    return commandTable.get(commandsAndArgs.get(0));
  }

  /**
   * This method returns the valid arguments the user entered
   * 
   * @return the List of arguments
   */
  public List<String> getArguments() {
    // return everything in the array aside from the first element
    return commandsAndArgs.subList(1, commandsAndArgs.size());
  }
}
