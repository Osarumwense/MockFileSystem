package test;

import driver.*;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * A test class to test the Echo command and class.
 * 
 * @author Radu Laudat and Ruyin Zhang
 *
 */
public class EchoTest {
  Echo tempEcho;
  Shell tempShell;
  LsCommand ls;

  /**
   * A method to initialize new instances of Shell and Echo to be used by
   * individual test cases.
   */
  @Before
  public void setUp() {
    tempEcho = new Echo();
    tempShell = new Shell();
    ls = new LsCommand();
  }

  /**
   * A test case for the execute method with a one element content list input.
   * 
   * @throws InvalidSyntaxException
   */
  @Test
  public void testExecutionMethodPart1() throws InvalidSyntaxException {
    List<String> tempList = new ArrayList<String>();
    String value = "\"string1\"";
    tempList.add(value);
    String testResult = tempEcho.execute(tempList, tempShell);
    assertEquals(testResult, "string1");
  }

  /**
   * A test case for the execute method with a three element content list input.
   * 
   * @throws InvalidSyntaxException
   * @throws InvalidPathException
   */
  @Test
  public void testExecutionMethodPart2() throws InvalidSyntaxException {
    List<String> tempList2 = new ArrayList<String>();
    String value1 = "\"string2\"";
    tempList2.add(value1);
    tempList2.add(">");
    tempList2.add("secondFile");
    String testResult2 = tempEcho.execute(tempList2, tempShell);
    assertEquals("New File successfully created.", testResult2);
  }
}
