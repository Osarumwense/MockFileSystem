package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import driver.*;

/**
 * @author Ruyin Zhang and Radu Laudat A test class to test the Ls command and
 *         class in the driver package
 */
public class LsTest {
  LsCommand ls;
  Shell shell;
  MakeDirectory mkdir;
  ArrayList<String> command;
  File newFile;

  /**
   * A setup for the following test cases in which new objects are instantiated
   * for use by individual test cases.
   */
  @Before
  public void setUp() {
    ls = new LsCommand();
    shell = new Shell();
    mkdir = new MakeDirectory();
    command = new ArrayList<String>();
  }


  /**
   * @throws InvalidPathException A test case to test the Ls command when there
   *         are no directories under a a parent directory
   */
  @Test
  public void testLs() throws InvalidPathException {
    String actualResult = ls.execute(command, shell);
    String expectedResult = "";
    assertEquals(expectedResult, actualResult);
  }

  /**
   * @throws InvalidPathException A test case to test the Ls command when
   *         there's more than one directory under a parent directory
   */
  @Test
  public void testLs2() throws InvalidPathException {
    command.add("Desktop");
    mkdir.execute(command, shell);
    String actualResult = ls.execute(command, shell);
    String expectedResult = "Desktop: \n";
    assertEquals(expectedResult, actualResult);
  }

  /**
   * A test case for ls -R
   * 
   * @throws InvalidPathException
   */
  @Test
  public void testLsWithR() throws InvalidPathException {
    command.add("a");
    command.add("b");
    mkdir.execute(command, shell);
    newFile = new File("a.txt", "contents here");
    newFile = new File("b.txt", "I'm in b directory");
    ArrayList<String> args = new ArrayList<String>();
    args.add("-r");
    String actualResult = ls.execute(args, shell);
    String expectedResult = "a\nb\n\n/a: \n/b: \n";
    assertEquals(expectedResult, actualResult);
  }

}
