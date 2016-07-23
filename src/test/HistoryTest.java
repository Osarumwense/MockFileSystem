package test;

import driver.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;


/**
 * @author Ruyin Zhang, Radu Laudat
 *
 */
public class HistoryTest {
  History historyInstance;

  /**
   * A setup for the following test cases where a new History object is
   * instantiated for each individual test case
   */
  @Before
  public void setUp() {
    historyInstance = new History();
  }

  /**
   * A test case for the constructor, checks if it was created properly
   */
  @Test
  public void testConstructor1() {
    History historyInstance2 = new History();
    assertEquals(historyInstance.getHistoryList(),
        historyInstance2.getHistoryList());
  }

  /**
   * A test case to determine if the object was created as a History object, and
   * not as any other type of object found within the driver package or the Java
   * API.
   */
  @Test
  public void testConstructor2() {
    assertTrue(historyInstance instanceof History);
  }

  /**
   * A test case for determining if all the most recent command entries from the
   * getHistoryList method were correct.
   */
  @Test
  public void testGetAllHistory() {
    historyInstance.addEntry("abc");
    historyInstance.addEntry("def");
    historyInstance.addEntry("ghi");
    ArrayList<String> message = historyInstance.getHistoryList();
    assertEquals("abc", message.get(0));
    assertEquals("def", message.get(1));
    assertEquals("ghi", message.get(2));
  }

  /**
   * A similar test case as above, but instead retrieving the "n" most recent
   * command entries, where n is any number greater than or equal to 0.
   * 
   * @throws InvalidHistoryNumberException
   */
  @Test
  public void testGetPartialHistory() throws InvalidHistoryNumberException {
    historyInstance.addEntry("abc");
    historyInstance.addEntry("def");
    historyInstance.addEntry("ghi");
    String retrieve = historyInstance.getHistory(2);
    // Create a list storing the messages
    String[] message = retrieve.split("\n");
    assertEquals("2. def", message[0]);
    assertEquals("3. ghi", message[1]);
    assertEquals(2, message.length);
  }

  /**
   * A test case to determine if the AddEntry method works as intended.
   */
  @Test
  public void testAddEntry() {
    historyInstance.addEntry("abc");
    assertEquals(1, historyInstance.getHistoryList().size());
    assertEquals("abc", historyInstance.getHistoryList().get(0));
  }

}

