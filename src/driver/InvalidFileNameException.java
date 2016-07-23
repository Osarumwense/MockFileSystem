package driver;

/**
 * Exception class for when an invalid file name is given
 * 
 * @author Jason Ku
 */

public class InvalidFileNameException extends Exception {

  public InvalidFileNameException(String message) {
    super(message);
  }
}
