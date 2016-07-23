package driver;

/**
 * Exception class for when there is invalid syntax given
 * 
 * @author Jason Ku
 */

public class InvalidSyntaxException extends Exception {

  public InvalidSyntaxException(String message) {
    super(message);
  }
}
