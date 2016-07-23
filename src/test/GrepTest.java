/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import driver.*;


/**
 * @author osaru
 *
 */
public class GrepTest {

  private Grep grepper;
  private Shell shell;
  private Directory dir1;
  private Directory dir2;
  private File file;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    grepper = new Grep();
    shell = new Shell();
    dir1 = new Directory("d1");
    dir2 = new Directory("d2");
    file = new File("myfile", "hello \n 1234");
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {}

  /**
   * Test method for Testing Grep's recursive functionality on a nested file
   * 
   * @throws InvalidSyntaxException
   */
  @Test
  public void testExecuteRecursive() throws InvalidSyntaxException {
    // make the file a nested file
    shell.getRootDirectory().addChildDirectory(dir1);
    dir1.addChildDirectory(dir2);
    dir2.addChildFile(file);
    List<String> args;
    // instantiate regex and other parameters
    String regex = "[a-z]";
    regex = "\"" + regex + "\"";
    args = Arrays.asList("-R", regex, "/d1/d2");
    String actual = grepper.execute(args, shell);
    String expected = "/myfile: hello \n";
    assertEquals(expected, actual);

  }

  /**
   * Test method for Testing Grep's non recursive functionality on a file
   * 
   * @throws InvalidSyntaxException
   */
  @Test
  public void testExecuteNonRecursive() throws InvalidSyntaxException {
    // put the file in root dir
    shell.getRootDirectory().addChildFile(file);
    List<String> args;
    // instantiate arguments.
    String regex = "[0-9]";
    regex = "\"" + regex + "\"";
    args = Arrays.asList(regex, "myfile");
    String actual = grepper.execute(args, shell);
    String expected = "/myfile:  1234\n";
    assertEquals(expected, actual);

  }


}
