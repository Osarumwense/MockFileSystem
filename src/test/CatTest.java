package test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import driver.*;

/**
 * A test class for the Cat class in the driver package
 * 
 * @author Radu Laudat and Ruyin Zhang
 */
public class CatTest {
  Echo tempEcho;
  Cat readFile;
  ArrayList<String> tempCommand = new ArrayList<String>();
  Shell shell;

  /**
   * A setup for the following test case where new objects are instantiated and
   * input values are transmitted to already existing ones, to be used for all
   * individual test cases.
   * 
   * @throws InvalidSyntaxException
   */
  @Before
  public void setUp() throws InvalidSyntaxException {
    tempCommand.add("abc");
    tempCommand.add(">");
    tempCommand.add("abc.txt");
    shell = new Shell();
    tempEcho = new Echo();
    tempEcho.execute(tempCommand, shell);
    readFile = new Cat();
  }

  /**
   * A test case for the Cat command when a valid path is given
   * 
   * @throws InvalidFileNameException
   */
  @Test
  public void testCat()
      throws InvalidFileNameException, InvalidSyntaxException {
    Directory root = shell.getRootDirectory();
    File testFile = new File("abc.txt", "stuff here");
    root.addChildFile(testFile);
    ArrayList<String> file = new ArrayList<String>();
    file.add("abc.txt");
    shell.setCurrentDirectory(root);
    String message = readFile.execute(file, shell);
    assertEquals("stuff here\n\n\n", message);
  }

  /**
   * A test case for cat command when an invalid path is given
   * 
   * @throws InvalidFileNameException
   */
  @Test
  public void testCatInvalidPath()
      throws InvalidFileNameException, InvalidSyntaxException {
    ArrayList<String> file = new ArrayList<String>();
    file.add("abc.txt");
    String message = readFile.execute(file, shell);
    assertEquals(
        "The file name abc.txt is invalid please" + " try another\n\n\n",
        message);
  }

}
