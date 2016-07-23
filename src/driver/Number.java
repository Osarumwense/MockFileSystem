package driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Number implements Command {

  @Override
  public String execute(List<String> args, Shell shell)
      throws InvalidSyntaxException, InvalidHistoryNumberException,
      MalformedURLException, InvalidPathException, NoParentException,
      InvalidFileNameException, IOException {
    String output = "Not a valid command";
    Command command;
    List<String> arguments;
    Interpreter interpreter = shell.getInterpreter();
    // Check if the syntax is followed
    if (args.size() == 1) {
      // Get the history list
      ArrayList<String> history = shell.getHistoryList();
      // Try to get the requested index number
      try {
        // Retrieve the index number
        String numberLine = history.get(Integer.parseInt(args.get(0)) - 1);
        try {
          interpreter.interpret(numberLine);
          command = interpreter.getCommand();
          arguments = interpreter.getArguments();
          output = command.execute(arguments, shell);
        } catch (InvalidCommandException ex) {
          System.out.println(ex.getMessage());
        }
      } catch (IndexOutOfBoundsException ex) {
        System.out.println(ex.getMessage());
      }
    } else {
      throw new InvalidSyntaxException(
          "Not valid syntax for the ! " + "command");
    }
    return output;
  }

  /**
   * A method that emulates the number method given an ArrayList<String> of
   * previous inputs
   * 
   * @param entry An int that represents the entry number to retrieve
   * @param inputs An ArrayList<String> of the past inputs
   * @return String
   */
  public String getSpecificEntryEmulator(int entry, ArrayList<String> inputs) {
    // Check if the requested entry is valid;
    if (entry > 0 && entry <= inputs.size()) {
      return inputs.get(entry - 1);
    } else {
      return "Invalid entry number requested";
    }
  }
}
