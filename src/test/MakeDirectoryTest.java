package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import driver.InvalidPathException;
import driver.LsCommand;
import driver.MakeDirectory;
import driver.Shell;

/**
 * A test class to test the Mkdir class in the driver package
 * 
 * @author Ruyin Zhang and Radu Laudat
 */
public class MakeDirectoryTest {
  Shell shell;
  MakeDirectory newDir;
  LsCommand list;
  ArrayList<String> currentDir;
  ArrayList<String> directory;

  /**
   * A setup method for the following test cases to instantiate all needed
   * objects for individual test cases.
   */
  @Before
  public void setUp() {
    shell = new Shell();
    newDir = new MakeDirectory();
    currentDir = new ArrayList<String>();
    directory = new ArrayList<String>();
  }

  /**
   * A test case to test if the Mkdir class method can change the current
   * directory successfully.
   * 
   * @throws InvalidPathException
   */
  @Test
  public void testMkdir() throws InvalidPathException {
    directory.add("mkdir");
    directory.add("Desktop");
    String message = newDir.execute(directory, shell);
    String expectedResult = "";
    assertEquals(expectedResult, message);

  }

}
