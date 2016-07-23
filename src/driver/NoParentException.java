package driver;

/**
 * Exception class for when an there is no parent directory
 * 
 * @author Jason Ku
 */


public class NoParentException extends Exception {

  public NoParentException(String message) {
    super(message);
  }
}
