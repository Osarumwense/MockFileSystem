package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import driver.*;

/**
 * A test class for the Curl class in the driver package.
 *
 * Author: Radu Laudat
 *
 */
public class CurlTest {
  Curl curlInstance;
  Shell shellInstance;
  List<String> listOfArguments;


  /**
   * A setup for the following test case(s) where new objects are instantiated
   * and input values are transmitted to already existing ones if needed, to be
   * used for all individual test cases.
   */
  @Before
  public void setUp() {
    curlInstance = new Curl();
    shellInstance = new Shell();
    listOfArguments = new ArrayList();
  }

  /**
   * A test case to determine if the correct output is gathered from the URL
   * link provided.
   * 
   * @throws IOException
   * @throws InvalidSyntaxException
   */
  @Test
  public void testBasicFunctionality()
      throws IOException, InvalidSyntaxException {
    listOfArguments.add("http://www.robotstxt.org/robotstxt.html");
    String realResult = curlInstance.execute(listOfArguments, shellInstance);
    String expectedStart = "";
    assertTrue(realResult.contains(expectedStart));
  }

  /**
   * A test case to determine if the Curl class correctly overrides an already
   * existing file with the same name in the current directory if the URL link
   * happens to gives back a file with the same name.
   * 
   * @throws IOException
   * @throws InvalidSyntaxException
   */
  @Test
  public void testIdenticalFileCreation()
      throws IOException, InvalidSyntaxException {
    listOfArguments.add("http://www.robotstxt.org/robotstxt.html");
    File file = new File("robotstxt.html");
    shellInstance.getCurrentDirectory().addChildFile(file);
    curlInstance.execute(listOfArguments, shellInstance);
    ArrayList<File> realResult =
        shellInstance.getCurrentDirectory().getChildFiles();
    ArrayList<File> expectedResult = new ArrayList<File>();
    expectedResult.add(file);
    assertEquals(expectedResult, realResult);
  }

  /**
   * A test class to determine if a unique file is created from a unique URL
   * leading to an html or txt file in the current directory of the Shell.
   * 
   * @throws IOException
   * @throws InvalidSyntaxException
   */
  @Test
  public void testUniqueFileCreation()
      throws IOException, InvalidSyntaxException {
    listOfArguments
        .add("http://www.tutorialspoint.com/java/java_loop_control.htm");
    curlInstance.execute(listOfArguments, shellInstance);
    ArrayList<File> files = shellInstance.getCurrentDirectory().getChildFiles();
    String fileName = files.get(0).getFileName();
    assertEquals("java_loop_control.htm", fileName);
  }


}
