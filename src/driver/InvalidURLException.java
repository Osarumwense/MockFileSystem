package driver;

/**
 * Exception class for when the user enters an invalid URL
 * 
 * @author Radu Laudat
 *
 */
public class InvalidURLException extends Exception {
  public InvalidURLException(String message) {
    super(message);
  }

}
