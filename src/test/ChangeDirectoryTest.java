package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import driver.*;

/**
 * @author Ruyin Zhang and Radu Laudat A test class for the Cd class in the
 *         driver package
 */
public class ChangeDirectoryTest {
  Shell shell;
  ArrayList<String> directory;
  MakeDirectory mkdir;
  ChangeDirectory cd;
  PrintWorkingDirectory pwd;

  /**
   * A setup method for the following individual test cases, where new objects
   * are instantiated and inputs are added to the already created objects
   */
  @Before
  public void setUp() {
    directory = new ArrayList<String>();
    shell = new Shell();
    mkdir = new MakeDirectory();
    cd = new ChangeDirectory();
    pwd = new PrintWorkingDirectory();
    directory.add("Desktop");
  }

  /**
   * A test case for the cd command to determine if the directory has been
   * changed correctly.
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   * @throws NoParentException
   */
  @Test
  public void testCd()
      throws InvalidPathException, InvalidSyntaxException, NoParentException {
    mkdir.execute(directory, shell);
    cd.execute(directory, shell);
    Directory dir = shell.getCurrentDirectory();
    String expectedResult = "/" + dir.getDirectoryName() + "/";
    String actualResult = pwd.execute(directory, shell);
    assertEquals(expectedResult, actualResult);

  }

}
