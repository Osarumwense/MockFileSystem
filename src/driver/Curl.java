package driver;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * A class to represent the curl command and all its functionalities.
 * 
 * @author Radu Laudat
 *
 */
public class Curl implements Command {
  // instantiate all required instance variables for the class with their types.
  private URL userURL;
  private String fileName;
  private String fileContents = "";

  public String execute(List<String> argsList, Shell shell)
      throws IOException, InvalidSyntaxException {
    // attempt to connect to the URL, add the name of the file at the end
    // of the URL to fileName and set userURL to the the URL provided as input.
    try {
      userURL = new URL(argsList.get(0));
      String tempFileName = userURL.getFile();
      String[] parts = tempFileName.split("/");
      fileName = parts[parts.length - 1];
      URLConnection userUrlConnection = userURL.openConnection();
      userUrlConnection.connect();
    }
    // if the URL cannot be connected to because of an Invalid URL, throw a
    // MalformedURLException along with an individualized message.
    catch (MalformedURLException e) {
      throw new MalformedURLException(
          "Invalid URL address, please check that you have entered a valid URL"
              + " address leading to a file.");
    }
    // if the URL cannot be connected to for any other reason, throw an
    // IOException.
    catch (IOException e) {
      throw new IOException("Connection to URL cannot be established");
    }
    // attempt to feed fileContents with the contents of the URL at the
    // specified end file.
    try {
      BufferedReader input =
          new BufferedReader(new InputStreamReader(userURL.openStream()));
      String reader;
      while ((reader = input.readLine()) != null) {
        fileContents += reader;
      }
    }
    // If fileContents cannot be fed with the URl contents, throw an
    // IOException.
    catch (IOException e) {
      throw new IOException("An error occured in processing URL contents");
    }
    // Make a File object out of the fileName and fileContents that were
    // gathered from the URL.
    File urlContentFile = new File(fileName, fileContents);
    // set overwrite conditions for if a File object with the same name as the
    // File object developed from the URL contents exists in the current
    // directory of the shell.
    for (File fileObject : shell.getCurrentDirectory().getChildFiles()) {
      if (fileObject.getFileName().equals(fileName)) {
        fileObject.edit(fileContents, ">");
        return "";
      }
    }
    // if no overwriting needs to occur, simply add the urlContentFile to the
    // current working directory as a child File.
    shell.getCurrentDirectory().addChildFile(urlContentFile);
    return "";
  }

}

