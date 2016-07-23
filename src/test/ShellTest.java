package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import driver.*;

/**
 * A class to Test the Shell class properly, including all its methods
 * 
 * @author osaru and Radu Laudat and Ruyin Zhang
 *
 */
public class ShellTest {

  private Shell shell;
  private MakeDirectory writer;
  private ChangeDirectory changer;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  /**
   * A method to set up a new Shell before each test case is run.
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    shell = new Shell();
  }

  @After
  public void tearDown() throws Exception {}

  /**
   * A test to check if all the attributes are instantiated properly upon
   * creation
   */
  @Test
  public void testShell() {
    assertTrue(shell.getStatus());
    assertEquals(shell.getHistoryList().size(), 0);
    assertEquals(shell.getRootDirectory().getParent(), null);
    assertEquals(shell.getCurrentDirectory(), shell.getRootDirectory());
  }



  /**
   * A test to check if the getStatus method works properly
   */
  @Test
  public void testGetStatus() {
    // As long as the shell is awake the status should be True
    assertTrue(shell.getStatus());
  }

  /**
   * A test method to check if getCurrentDirectory works properly after calling
   * Cd
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   * @throws NoParentException
   */
  @Test
  public void testGetCurrentDirectoryAfterCd()
      throws InvalidPathException, InvalidSyntaxException, NoParentException {
    // Create a new directory then cd into it.
    writer = new MakeDirectory();
    changer = new ChangeDirectory();
    // Create the path for the directory.
    ArrayList<String> args = new ArrayList<String>();
    args.add("NewDir");
    writer.execute(args, shell);
    Directory newDir = shell.getRootDirectory().getPathDirectory("NewDir");
    changer.execute(args, shell);
    assertEquals(newDir, shell.getCurrentDirectory());
  }

  /**
   * A test method to check if GetCurrentDirectory works before any user
   * commands are called.
   */
  @Test
  public void testGetCurrentDirectory() {
    // The method should return the root directory since cd hasn't been called
    assertEquals(shell.getCurrentDirectory().getDirectoryName(), "/");

  }

  /**
   * A test method to check if SetCurrentDirectory works properly
   * 
   * @throws InvalidPathException
   */
  @Test
  public void testSetCurrentDirectory() throws InvalidPathException {
    // Create a new Directory and set shell to that directory
    ArrayList<String> args = new ArrayList<String>();
    args.add("NewDir");
    writer = new MakeDirectory();
    writer.execute(args, shell);
    Directory newDir = shell.getRootDirectory().getPathDirectory("NewDir");
    shell.setCurrentDirectory(newDir);
    assertEquals(newDir, shell.getCurrentDirectory());
  }

  /**
   * A method to check whether getRootDirectory works properly
   * 
   * @throws InvalidPathException
   */
  @Test
  public void testGetRootDirectory() throws InvalidPathException {
    // Create a new Directory and set shell to that directory
    ArrayList<String> args = new ArrayList<String>();
    args.add("NewDir");
    writer = new MakeDirectory();
    writer.execute(args, shell);
    Directory newDir = shell.getRootDirectory().getPathDirectory("NewDir");
    shell.setCurrentDirectory(newDir);
    assertEquals(shell.getCurrentDirectory().getParent(),
        shell.getRootDirectory());
  }

  /**
   * A test to see if the full path of the current directory is rightly returned
   * 
   * @throws InvalidPathException
   * @throws NoParentException
   * @throws InvalidSyntaxException
   */
  @Test
  public void testGetFullPath()
      throws InvalidPathException, InvalidSyntaxException, NoParentException {
    writer = new MakeDirectory();
    changer = new ChangeDirectory();
    ArrayList<String> args = new ArrayList<String>();
    args.add("NewDir");
    writer.execute(args, shell);
    changer.execute(args, shell);
    assertEquals("/NewDir/", shell.getFullPath());
  }

  /**
   * A Method to test if the fullPath of the shell is properly changed
   */
  @Test
  public void testChangeFullPath() {
    shell.changeFullPath("/newPath/");
    assertEquals("/newPath/", shell.getFullPath());
  }

}
