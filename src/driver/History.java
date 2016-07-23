package driver;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the history of inputs given by a user in the shell
 * 
 * @author Jason and Osaru
 * 
 */
public class History implements Command {

  private ArrayList<String> historyList;

  @Override
  /**
   * This method executes the history command
   * 
   * @return the list of previous commands entered.
   */

  public String execute(List<String> args, Shell shell)
      throws InvalidHistoryNumberException, InvalidSyntaxException {
    String history;
    // Update the HistoryList with the list from the shell
    historyList = shell.getHistoryList();
    // Determine whether a number has been passed in or not
    if (args.size() == 0) {
      history = getHistory();
    } else if (args.size() > 1) {
      throw new InvalidSyntaxException(
          "Invalid syntax for the " + "History command");
    } else {
      String entryNum = args.get(0);
      history = getHistory((Integer.parseInt(entryNum)));
    }
    return history;
  }

  public History() {
    // Initiate the historyList by creating an empty ArrayList
    historyList = new ArrayList<String>();
  }

  /**
   * Method to get all history of inputs from user by printing them out
   * 
   * @return ArrayList<String> A list of all of the past inputs in the form of
   *         an ArrayList
   */
  public String getHistory() {
    String message = "";
    int i = 1;
    // Return the history list messages
    for (String nextMessage : historyList) {
      message = message + (i + ". " + nextMessage + "\n");
      i++;
    }
    return message;
  }

  /**
   * Method to retrieve the last number of entries
   * 
   * @param number The entry number you want to retrieve
   * @return String The requested history entry if defined, otherwise error
   *         message
   * @throws InvalidHistoryNumberException
   */
  public String getHistory(int number) throws InvalidHistoryNumberException {
    String message = "";
    // Check if a negative entry or 0 is given
    if (number < 1) {
      message = "Please enter a number greater than 1";
    } else {
      // Try to retrieve the requested number of entries
      try {
        // Get the size of the list
        int elements = historyList.size() - number;
        for (int i = 0; i < number; i++) {
          message = message
              + ((elements + 1 + i + ". " + historyList.get(elements + i)));
          // If i is one less than number, then we don't need a new line
          if (i != number - 1) {
            message = message + "\n";
          }
        }
      }
      // If not retrievable then must not exist in history
      catch (IndexOutOfBoundsException e) {
        throw new InvalidHistoryNumberException("Not a valid history number");
      }
    }
    return message;
  }

  /**
   * Method to add a history entry
   * 
   * @param entry The message/command that wants to be added
   */
  public void addEntry(String entry) {
    // Add to the list
    historyList.add(entry);
  }

  /**
   * A method to return a List of the History of Shell commands entered
   * 
   * @return
   */
  public ArrayList<String> getHistoryList() {
    return historyList;
  }

}
