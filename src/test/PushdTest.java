package test;

import driver.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Radu Laudat and Ruyin Zhang A test class for the Push class in the
 *         driver package
 */
public class PushdTest {
  Shell newShell;
  ArrayList<String> newArray;
  Pushd tempPushd;

  /**
   * A setup for the following test cases in which new objects are instantiated
   * for use by individual test cases.
   */
  @Before
  public void setUp() {
    newShell = new Shell();
    newArray = new ArrayList<String>();
    tempPushd = new Pushd();
  }

  /**
   * A test case to test if the pushd class can push the item properly
   * 
   * @throws InvalidSyntaxException
   * @throws InvalidPathException
   */
  @Test(expected = InvalidPathException.class)
  public void testAbsolutePathError()
      throws InvalidSyntaxException, InvalidPathException {
    newArray.add("/home/dir/dir1/dir2");
    newArray.add(">");
    newArray.add("file contents");
    String executeString = tempPushd.execute(newArray, newShell);
  }

}
