package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import driver.ChangeDirectory;
import driver.Echo;
import driver.InvalidPathException;
import driver.InvalidSyntaxException;
import driver.LsCommand;
import driver.MakeDirectory;
import driver.Move;
import driver.NoParentException;
import driver.Shell;

/**
 * A test class to test the Mv command and class.
 * 
 * @author Ruyin Zhang
 *
 */

public class MoveTest {

  Shell shell;
  ArrayList<String> directory;
  ArrayList<String> file;
  ArrayList<String> anotherfile;
  MakeDirectory mkdir;
  LsCommand ls;
  ChangeDirectory cd;
  Move mv;
  Echo echo;

  /**
   * A method to initialize new instances of Shell and Mv to be used by
   * individual test cases.
   */
  @Before
  public void setUp() {
    shell = new Shell();
    directory = new ArrayList<String>();
    file = new ArrayList<String>();
    anotherfile = new ArrayList<String>();
    mkdir = new MakeDirectory();
    ls = new LsCommand();
    cd = new ChangeDirectory();
    mv = new Move();
    echo = new Echo();
  }

  /**
   * A test case for moving one directory to the other directory
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   * @throws NoParentException
   */
  @Test
  public void mvDirectoryToDirectorytest()
      throws InvalidPathException, InvalidSyntaxException, NoParentException {
    directory.add("a");
    directory.add("b");
    mkdir.execute(directory, shell);
    ArrayList<String> listForMv = new ArrayList<String>();
    listForMv.add("a");
    listForMv.add("b");
    mv.execute(listForMv, shell);
    ArrayList<String> listForls = new ArrayList<String>();
    listForls.add("");
    String actualItems = ls.execute(listForls, shell);
    String expectedItems = "/: " + "\n" + "b" + "\n";
    assertEquals(expectedItems, actualItems);

  }

  /**
   * A test case for moving one file to the other directory
   * 
   * @throws InvalidSyntaxException
   * @throws InvalidPathException
   * @throws NoParentException
   */
  @Test
  public void mvFileToDirectorytest()
      throws InvalidSyntaxException, InvalidPathException, NoParentException {
    directory.add("a");
    String value1 = "\"string2\"";
    file.add(value1);
    file.add(">");
    file.add("secondFile");
    String testResult2 = echo.execute(file, shell);
    assertEquals("New File successfully created.", testResult2);
    mkdir.execute(directory, shell);
    ArrayList<String> directoryForMv = new ArrayList<String>();
    directoryForMv.add("secondFile");
    directoryForMv.add("a");
    mv.execute(directoryForMv, shell);
    ArrayList<String> directoryForcd = new ArrayList<String>();
    directoryForcd.add("a");
    cd.execute(directoryForcd, shell);
    directoryForcd.add(0, "");
    String actualItems = ls.execute(directoryForcd, shell);
    String expectedItems = "a: " + "\n" + "secondFile" + "\n";
    assertEquals(expectedItems, actualItems);
  }

  /**
   * A test case for renaming one file to the other file's name
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   */
  @Test
  public void mvFileToFileTest()
      throws InvalidPathException, InvalidSyntaxException {
    String value1 = "\"string1\"";
    file.add(value1);
    file.add(">");
    file.add("firstFile");
    String testResult1 = echo.execute(file, shell);
    assertEquals("New File successfully created.", testResult1);
    String value2 = "\"string2\"";
    anotherfile.add(value2);
    anotherfile.add(">");
    anotherfile.add("secondFile");
    String testResult2 = echo.execute(anotherfile, shell);
    assertEquals("New File successfully created.", testResult2);
    ArrayList<String> directoryForls = new ArrayList<String>();
    ArrayList<String> directoryForMv = new ArrayList<String>();
    directoryForMv.add("firstFile");
    directoryForMv.add("secondFile");
    mv.execute(directoryForMv, shell);
    String newPathItems = ls.execute(directoryForls, shell);
    String expectedItems = "secondFile" + "\n";
    assertEquals(expectedItems, newPathItems);


  }
}
