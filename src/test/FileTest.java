package test;

import driver.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

/**
 * A test class that contains methods to test individual method modules of the
 * File class in the driver package.
 * 
 * @author Radu Laudat and Ruyin Zhang
 *
 */
public class FileTest {
  File newFile;

  /**
   * A method to instantiate a new File object with identical input for
   * individual test cases to use.
   */
  @Before
  public void setUp() {
    newFile = new File("ABC", "DEF");
  }

  /**
   * A test case to test if the file was created correctly with name "abc" and
   * contents "def"
   */
  @Test
  public void testFileConstructor1() {
    File file = new File("abc", "def");
    assertEquals("abc", file.getFileName());
    assertEquals("def", file.getFileContent());
  }

  /**
   * A test case to see if the file created an empty file with name "ghi"
   */
  @Test
  public void testFileConstructor2() {
    File file = new File("ghi");
    assertEquals("ghi", file.getFileName());
  }

  /**
   * A test case for when someone uses the echo > command, expect the file
   * contentes erased and overwritten
   * 
   * @throws InvalidSyntaxException
   */
  @Test
  public void testEditWithGreaterSymbol() throws InvalidSyntaxException {
    newFile.edit("new stuff", ">");
    assertEquals("new stuff", newFile.getFileContent());
  }

  /**
   * A test case for when someone uses the echo >> command, expect the file to
   * have its contents appended
   * 
   * @throws InvalidSyntaxException
   */
  @Test
  public void testEditWithTwoGreaterSymbols() throws InvalidSyntaxException {
    newFile.edit("add this", ">>");
    assertEquals("DEF\nadd this", newFile.getFileContent());
  }

  /**
   * A test case for when the printContent method is called on
   */
  @Test
  public void testprintContent() {
    assertEquals("DEF\n\n\n", newFile.printContent());
  }

  /**
   * A test case to see if the printContent method works after edit has been
   * called
   * 
   * @throws InvalidSyntaxException
   */
  @Test
  public void testprintContentAfterEdit() throws InvalidSyntaxException {
    newFile.edit("new stuff here", ">>");
    assertEquals("DEF\nnew stuff here\n\n\n", newFile.printContent());
  }

  /**
   * A test case to see if the getFileName is correct
   */
  @Test
  public void testgetFileName() {
    assertEquals(newFile.getFileName(), "ABC");
  }

}


