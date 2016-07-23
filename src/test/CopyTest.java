package test;

import driver.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * A test class for the Cp class in the driver package.
 *
 * Author: Ruyin Zhang
 *
 */
public class CopyTest {

  Shell shell;
  ArrayList<String> directory;
  ArrayList<String> file;
  ArrayList<String> anotherfile;
  MakeDirectory mkdir;
  LsCommand ls;
  ChangeDirectory cd;
  Copy cp;
  Echo echo;

  /**
   * A method to initialize new instances of Shell and Cp to be used by
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
    cp = new Copy();
    echo = new Echo();
  }

  /**
   * A test case for copying one directory to the other directory
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   * @throws NoParentException
   */
  @Test
  public void cpDirectoryToDirectorytest()
      throws InvalidPathException, InvalidSyntaxException, NoParentException {
    directory.add("a");
    directory.add("b");
    mkdir.execute(directory, shell);
    ArrayList<String> directoryForcd = new ArrayList<String>();
    directoryForcd.add(directory.get(1));
    cd.execute(directoryForcd, shell);
    ArrayList<String> newDirectory = new ArrayList<String>();
    newDirectory.add("c");
    mkdir.execute(newDirectory, shell);
    ArrayList<String> backToRoot = new ArrayList<String>();
    backToRoot.add("..");
    cd.execute(backToRoot, shell);
    ArrayList<String> directoryForCp = new ArrayList<String>();
    directoryForCp.add("a");
    directoryForCp.add("b");
    cp.execute(directoryForCp, shell);
    ArrayList<String> rootDirectory = new ArrayList<String>();
    rootDirectory.add("/");
    String originalLocationItems = ls.execute(rootDirectory, shell);
    String expectedItems = "/:" + "\n" + "a" + "\n" + "b" + "\n" + "\n";
    assertEquals(expectedItems, originalLocationItems);
    cd.execute(directoryForcd, shell);
    directoryForcd.add(0, "");
    String newPathItems = ls.execute(directoryForcd, shell);
    expectedItems = "b: " + "\n" + "c" + "\n" + "a" + "\n";
    assertEquals(expectedItems, newPathItems);
  }

  /**
   * A test case for copying one file to the other directory
   * 
   * @throws InvalidSyntaxException
   * @throws InvalidPathException
   * @throws NoParentException
   */
  @Test
  public void cpFileToDirectorytest()
      throws InvalidSyntaxException, InvalidPathException, NoParentException {
    directory.add("a");
    String value1 = "\"string2\"";
    file.add(value1);
    file.add(">");
    file.add("secondFile");
    String testResult2 = echo.execute(file, shell);
    assertEquals("New File successfully created.", testResult2);
    mkdir.execute(directory, shell);
    ArrayList<String> directoryForCp = new ArrayList<String>();
    directoryForCp.add("secondFile");
    directoryForCp.add("a");
    cp.execute(directoryForCp, shell);
    ArrayList<String> directoryForcd = new ArrayList<String>();
    directoryForcd.add("a");
    cd.execute(directoryForcd, shell);
    directoryForcd.add(0, "");
    String actualItems = ls.execute(directoryForcd, shell);
    String expectedItems = "a: " + "\n" + "secondFile" + "\n";
    assertEquals(expectedItems, actualItems);
  }

  /**
   * A test case for copying one file with the name of the other file's name
   * 
   * @throws InvalidPathException
   * @throws InvalidSyntaxException
   */
  @Test
  public void cpFileToFileTest()
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
    ArrayList<String> directoryForCp = new ArrayList<String>();
    directoryForCp.add("firstFile");
    directoryForCp.add("secondFile");
    cp.execute(directoryForCp, shell);
    String newPathItems = ls.execute(directoryForls, shell);
    String expectedItems = "firstFile" + "\n" + "secondFile" + "\n";
    assertEquals(expectedItems, newPathItems);

  }

}
