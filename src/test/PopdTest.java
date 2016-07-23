package test;

/**
 * This class Tests the popd command
 * 
 * @author osaru
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import driver.*;

/**
 * A class to Test the Popd java command class
 * 
 * @author Osaru and Ruyin Zhang and Radu Laudat
 *
 */
public class PopdTest {

  private Shell shell;
  private MakeDirectory writer;
  private Pushd pusher;
  private Popd popper;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  /**
   * A method to instantiate a few needed objects before Tests methods are
   * called
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    popper = new Popd();
    shell = new Shell();
    writer = new MakeDirectory();
    pusher = new Pushd();
  }

  @After
  public void tearDown() throws Exception {}

  /**
   * A test method to see if Popd pops out of the directoryStack properly
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   */
  @Test
  public void testPopExecute()
      throws InvalidSyntaxException, InvalidPathException {
    ArrayList<String> path = new ArrayList<String>();
    path.add("NewDir");
    // make a new directory in this shell
    writer.execute(path, shell);
    pusher.execute(path, shell);
    popper.execute(path, shell);
    assertEquals(shell.getRootDirectory(), shell.getCurrentDirectory());
  }

  /**
   * A test method to see if Popd catches the EmptyStackException Exception and
   * prints out the right error message.
   */
  @Test
  public void testPopExecuteEmptyStack() {
    ArrayList<String> path = new ArrayList<String>();
    path.add("NewDir");
    assertEquals(popper.execute(path, shell),
        "The stack is empty, no previous" + " directory to change into");
  }
}
