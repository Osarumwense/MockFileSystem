package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import driver.ChangeDirectory;
import driver.MakeDirectory;
import driver.PrintWorkingDirectory;
import driver.Shell;

/**
 * @author Ruyin Zhang and Radu Laudat A test class for the Pwd class in the
 *         driver package
 */
public class PrintWorkingDirectoryTest {
  MakeDirectory mkdir;
  Shell shell;
  PrintWorkingDirectory currentDir;
  ChangeDirectory changeDir;
  ArrayList<String> argument;

  /**
   * A setup method for the following test cases in which new objects are
   * instantiated for use by individual test cases.
   */
  @Before
  public void setUp() {
    argument = new ArrayList<String>();
    mkdir = new MakeDirectory();
    shell = new Shell();
    currentDir = new PrintWorkingDirectory();
  }

  /**
   * A test case to test if the Pwd class can return a correct message about the
   * current directory
   */
  @Test
  public void testExecute() {
    String expectedResult = "/";
    String actualResult = currentDir.execute(argument, shell);
    assertEquals(expectedResult, actualResult);
  }

}
