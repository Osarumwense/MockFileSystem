// **********************************************************
// Assignment2:
// Student1: Osaru Ize-Iyamu
// UTORID user_name:iyamuos2
// UT Student #: 1001508665
// Author: Osaru Ize-Iyamu
//
// Student2: Jason Ku
// UTORID user_name: kujason
// UT Student #: 1002402820
// Author: Jason Ku
//
// Student3: Radu Laudat
// UTORID user_name: laudatra
// UT Student #: 1002394063
// Author: Radu Laudat
//
// Student4: Ruyin Zhang
// UTORID user_name: zhan2400
// UT Student #: 1002205883
// Author: Ruyin Zhang
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

public class JShell {

  public static void main(String[] args) {
    // Create and call the shell for the user to interact with
    Shell mainShell = new Shell();
    // Continue to keep the shell opened as long as the user does not close
    // it
    while (mainShell.getStatus()) {
      mainShell.display();
    }

  }

}
