package driver;

/**
 * Exception class for when an invalid history number is given
 * 
 * @author Jason Ku
 */

public class InvalidHistoryNumberException extends Exception {

  InvalidHistoryNumberException(String message) {
    super(message);
  }
}
