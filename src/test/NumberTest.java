package test;

import driver.*;
import driver.Number;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NumberTest {
  Number numberInstance;

  @Before
  public void setUp() throws Exception {
    // Create a new number and shell instance
    numberInstance = new Number();;
  }

  /**
   * A test case to see if Number class works as intended
   */
  @Test
  public void testValidInput() {
    // Check to see if a valid input works in the number class
    ArrayList<String> mockInputs = new ArrayList<String>();
    mockInputs.add("1. history");
    mockInputs.add("2. mkdir a");
    String expected = numberInstance.getSpecificEntryEmulator(2, mockInputs);
    assertEquals("2. mkdir a", expected);
  }

  /**
   * Testing an invalid negative entry
   */
  @Test
  public void testInvalidNegativeInput() {
    ArrayList<String> mockInputs = new ArrayList<String>();
    mockInputs.add("1. mkdir a b");
    mockInputs.add("2. pwd");
    mockInputs.add("3. ls");
    String expected = numberInstance.getSpecificEntryEmulator(-1, mockInputs);
    assertEquals("Invalid entry number requested", expected);
  }

  /**
   * A test case for testing an invalid out of range entry
   */
  public void testInvalidOutOfRangeInput() {
    ArrayList<String> mockInputs = new ArrayList<String>();
    mockInputs.add("1. man cd");
    String expected = numberInstance.getSpecificEntryEmulator(2, mockInputs);
    assertEquals("Invalid entry number requested", expected);
  }

}
