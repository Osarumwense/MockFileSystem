package test;

import driver.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

/**
 * A class that contains methods to test the directoryStack class.
 * 
 * @author Radu Laudat and Ruyin Zhang
 *
 */
public class DirectoryStackTest {
  DirectoryStack stackInstance;

  /**
   * A method to instantiate a new directoryStack object for individual test
   * cases to use.
   */
  @Before
  public void setUp() {
    stackInstance = new DirectoryStack();
  }

  /**
   * A test case for the push method in the directoryStack class.
   */
  @Test
  public void testPush() {
    Directory testDir = new Directory("testDir");
    stackInstance.push(testDir);
    assertTrue((stackInstance.direcStack.peek()) == testDir);
  }

  /**
   * A test case to determine if the stack object is empty upon initial
   * instantiation.
   */
  @Test
  public void testEmptyOnConstruction() {
    Directory testDir2 = new Directory("testDir2");
    assertTrue(stackInstance.direcStack.empty());
  }

  /**
   * A test case to determine if the stack object is holding and returning added
   * directory objects in the correct stack push and pop order.
   */
  @Test
  public void testObjectRetainment() {
    ArrayList<Directory> tempList = new ArrayList<Directory>();
    Directory testDir3 = new Directory("testDir3");
    Directory testDir4 = new Directory("testDir4");
    Directory testDir5 = new Directory("testDir5");
    stackInstance.push(testDir3);
    stackInstance.push(testDir4);
    stackInstance.push(testDir5);
    for (int i = 1; i != 4; i += 1) {
      tempList.add(stackInstance.pop());
    }
    assertEquals(tempList.size(), 3);
  }

  /**
   * A test case to determine if the stack object is empty upon pushing x
   * objects and popping x objects afterwards, where x is any integer > 0.
   */
  @Test
  public void testEmptinessAfterPopping() {
    Directory testDir6 = new Directory("testDir6");
    Directory testDir7 = new Directory("testDir7");
    Directory testDir8 = new Directory("testDir8");
    Directory testDir9 = new Directory("testDir9");
    stackInstance.push(testDir6);
    stackInstance.push(testDir7);
    stackInstance.push(testDir8);
    stackInstance.push(testDir9);
    for (int i = 1; i != 5; i += 1) {
      stackInstance.pop();

    }

    assertTrue(stackInstance.direcStack.empty());
  }


}
