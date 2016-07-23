package test;

import driver.*;
import org.junit.*;
import java.util.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import static org.junit.Assert.*;

/**
 * @author Ruyin Zhang, Radu Laudat A test class to test the interpreter class
 *         in the driver
 */
public class InterpreterTest {
  Interpreter interpreterInstance;
  Set<String> commands = new HashSet<String>(Arrays.asList("mkdir", "cd", "ls",
      "pwd", "pushd", "popd", "history", "cat", "echo", "man"));
  Hashtable<String, Command> commandTable = new Hashtable<String, Command>(10);

  @Before
  public void setUp() {
    interpreterInstance = new Interpreter();
  }

  /**
   * Test case for the constructor, check if arguments list is empty and command
   * gives back a command instance
   */
  @Test
  public void testConstructor() {
    interpreterInstance.interpret("cd ");
    Command testCommand = interpreterInstance.getCommand();
    List<String> testArguments = interpreterInstance.getArguments();
    assertEquals("driver.ChangeDirectory", testCommand.getClass().getName());
    assertTrue(testArguments.isEmpty());
  }

  @Test
  public void getCommand() {
    interpreterInstance.interpret("echo ");
    Command testCommand = interpreterInstance.getCommand();
    assertEquals("driver.Echo", testCommand.getClass().getName());
  }

  @Test
  public void testInterpret() {
    interpreterInstance.interpret("mkdir /test/test2/");
    List<String> arguments = interpreterInstance.getArguments();
    assertEquals("/test/test2/", arguments.get(0));
  }

  /**
   * Test case for when an invalid command is given, expect an exception to be
   * thrown
   */
  @Test(expected = InvalidCommandException.class)
  public void testInvalidCommand() {
    interpreterInstance.interpret("notacommand argumentshere");
  }

  /**
   * Test case for getting arguments after interpreting
   */
  @Test
  public void testgetArguments() {
    interpreterInstance.interpret("mkdir myFirstDirectory");
    List<String> testList = interpreterInstance.getArguments();
    List<String> testList2 = new ArrayList<String>();
    testList2.add("myFirstDirectory");
    assertEquals(testList, testList2);
  }

  /**
   * Test case for no arguments
   */
  @Test
  public void testgetArgumentsWithNoArguments() {
    interpreterInstance.interpret("cd ");
    List<String> testList = interpreterInstance.getArguments();
    assertTrue(testList.isEmpty());
  }

}
