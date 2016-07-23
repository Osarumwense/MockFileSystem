package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.*;

public class DirectoryTest {

  Directory emptyDir;
  Directory nameDir;

  @Before
  public void setUp() {
    emptyDir = new Directory("");
    nameDir = new Directory("name");
  }

  /**
   * Test if the constructor properly works when creating a directory without a
   * name
   */
  @Test
  public void testDirectoryConstructorEmptyName() {
    String actual = emptyDir.getDirectoryName();
    assertEquals("", actual);
  }

  /**
   * A test case for when a directory is created with a name, "name"
   */
  @Test
  public void testDirectoryConstructorWithName() {
    String actual = nameDir.getDirectoryName();
    assertEquals("name", actual);
  }

  /**
   * A test case for when you add a child directory
   */
  @Test
  public void testAddChildDirectory() {
    emptyDir.addChildDirectory(nameDir);
    Directory actual = emptyDir.getPathDirectory("name");
    assertEquals(nameDir, actual);
  }

  /**
   * A test case for when you add a file as a child of a directory
   */
  @Test
  public void testAddChildFile() {
    File file = new File("test");
    emptyDir.addChildFile(file);
    File actual = emptyDir.getPathFile("test");
    assertEquals(file, actual);
  }

  /**
   * A test case for getting the name of a directory
   */
  @Test
  public void testGetDirectoryName() {
    assertEquals("", emptyDir.getDirectoryName());
  }

  /**
   * A test case to see if a directory is a child
   */
  @Test
  public void testIsChildPathDirectory() {
    emptyDir.addChildDirectory(nameDir);
    assertTrue(emptyDir.isChildPath("name", "directory"));
  }

  /**
   * A test case for seeing if a child path is valid when given a file
   */
  @Test
  public void testIsChildPathFile() {
    File test = new File("test");
    emptyDir.addChildFile(test);
    assertTrue(emptyDir.isChildPath("test", "file"));
  }

  /**
   * A test case for seeing if a child path is valid when given a file that is
   * under another directory
   */
  @Test
  public void testIsChildPathFileUnderAnotherDirectory() {
    File test = new File("test");
    emptyDir.addChildDirectory(nameDir);
    nameDir.addChildFile(test);
    assertTrue(emptyDir.isChildPath("name/test", "file"));
  }

  /**
   * A test case for seeing if a child path is valid when given a path that
   * wants to see if a directory exists under another
   */
  @Test
  public void testIsChildPathDirectoryUnderAnotherDirectory() {
    Directory test = new Directory("test");
    emptyDir.addChildDirectory(nameDir);
    nameDir.addChildDirectory(test);
    assertTrue(emptyDir.isChildPath("name/test/", "directory"));
  }

  /**
   * A test case for the getPathDirectory() method
   */
  @Test
  public void testGetPathDirectory() {
    emptyDir.addChildDirectory(nameDir);
    Directory actual = emptyDir.getPathDirectory("name");
    assertEquals(nameDir, actual);
  }

  /**
   * A test case for the getPathDirectory() method when given an invalid path
   */
  @Test
  public void testGetPathDirectoryInvalidPath() {
    Directory actual = emptyDir.getPathDirectory("Not a valid path");
    assertEquals(emptyDir, actual);
  }

  /**
   * A test case for the getPathFile() method
   */
  @Test
  public void testGetPathFile() {
    File test = new File("test");
    emptyDir.addChildFile(test);
    assertEquals(test, emptyDir.getPathFile("test"));
  }

  /**
   * A test case for the printAllChildren() method
   */
  @Test
  public void testPrintAllChildren() {
    Directory home = new Directory("home");
    File someFile = new File("someFile");
    home.addChildDirectory(nameDir);
    home.addChildDirectory(emptyDir);
    home.addChildFile(someFile);
    String actual = home.printAllChildren();
    assertEquals("name\n\nsomeFile\n", actual);
  }

  /**
   * A test case for the exists() method
   */
  @Test
  public void testExists() {
    emptyDir.addChildDirectory(nameDir);
    assertTrue(emptyDir.dirExists("name"));
  }

  /**
   * A test case for the getParent() method
   */
  @Test
  public void testGetParent() {
    emptyDir.setParent(nameDir);
    Directory actual = emptyDir.getParent();
    assertEquals(nameDir, actual);
  }

  /**
   * A test case for the setParent() method
   */
  @Test
  public void testSetParent() {
    emptyDir.setParent(nameDir);
    Directory actual = emptyDir.getParent();
    assertEquals(nameDir, actual);
  }

}
