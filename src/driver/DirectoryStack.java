package driver;

import java.util.*;

/**
 * An instance of a Stack to hold or give back directories when the pushd or
 * popd command is entered.
 * 
 * @author Radu Laudat
 *
 */

public class DirectoryStack {
  public Stack<Directory> direcStack = new Stack();
  private Stack<String> fullPaths = new Stack();

  /**
   * A method that pushes a directory into direcStack
   * 
   * @param dir The directory to be pushed in
   */
  public void push(Directory dir) {
    direcStack.push(dir);
  }

  /**
   * A method that gets the latest directory in the stack
   * 
   * @return
   */
  public Directory pop() {
    return direcStack.pop();
  }

  /**
   * A method that pushes a newFullPath to the fullPaths stack
   * 
   * @param newFullPath The fullPath to be pushed
   */
  public void addFullPath(String newFullPath) {
    fullPaths.push(newFullPath);
  }

  /**
   * A method that gets the latest or most recent fullPath from the stack
   * 
   * @return The most recent fullPath string
   */
  public String getLastFullPath() {
    return fullPaths.pop();
  }
}
