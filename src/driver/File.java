package driver;

/**
 * @author osaru A class to represent a File object in the MOCK file system
 */
public class File {

  private String fileName;
  private String fileContent;

  /**
   * a constructor to create a File object
   * 
   * @param name the name of the file
   */
  public File(String name, String content) {
    fileName = name;
    fileContent = content;
  }

  /**
   * an overloaded constructor to create a File object to create an empty file
   * 
   * @param name the name of the file
   */
  public File(String name) {
    fileName = name;
    fileContent = "";
  }

  /**
   * A method to edit the given file, this may overwrite or append to the file
   * depending on the symbol given.
   * 
   * @param file the File object to be edited
   * @param inputString the content to be appended or overwritten to the file
   * @param symbol could be '>' for overwrite or ">>" for append
   * @throws InvalidSyntaxException
   */
  public String edit(String inputString, String symbol)
      throws InvalidSyntaxException {
    String outputMessage = "";
    // check to see if we have > or >>
    if (symbol.equals(">")) {
      fileContent = inputString;
      outputMessage = "File has been successfully overwritten.";
    } else if (symbol.equals(">>")) {
      fileContent += "\n" + inputString;
      outputMessage = "File has been successfully updated";
    } else {
      throw new InvalidSyntaxException("Not valid syntax for command echo");
    }
    return outputMessage;
  }

  /**
   * This method returns the file's contents to be printed on the Shell window.
   * helper method for cat.
   */
  public String printContent() {
    // return the file's contents followed by 3 line breaks
    return (this.fileContent + "\n" + "\n" + "\n");
  }

  /**
   * A getter that returns the Contents of the File
   * 
   * @return the present Contents of the file
   */
  public String getFileContent() {
    return fileContent;
  }

  /**
   * This method returns the name of the current file
   * 
   * @return the filename stored in this file object
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * A method to set a new name to an existence file
   * 
   * @author Ruyin Zhang
   */
  public void setFileName(String filename) {
    this.fileName = filename;
  }

  /**
   * A method to set a new content to an existence file
   * 
   * @author Ruyin Zhang
   */
  public void setFileContent(String content) {
    this.fileContent = content;
  }
}
