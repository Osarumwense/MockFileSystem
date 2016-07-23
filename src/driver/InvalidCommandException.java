/**
 * 
 */
package driver;

/**
 * Exception class to be thrown when a user enters an Invalid command
 * 
 * @author osaru
 *
 */
public class InvalidCommandException extends RuntimeException {
  public InvalidCommandException(String message) {
    super(message);
  }
}
