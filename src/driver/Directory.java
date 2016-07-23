package driver;

import java.util.ArrayList;

/**
 * 
 * @author Jason
 *
 */

public class Directory {

  private ArrayList<File> childrenFiles;
  private ArrayList<Directory> childrenDirectories;
  private String directoryName;
  private Directory parent;

  public Directory(String Name) {
    // Initiate the children
    childrenFiles = new ArrayList<File>();
    childrenDirectories = new ArrayList<Directory>();
    // Set the name
    directoryName = Name;
  }

  /**
   * Add another directory object as a child of this directory
   * 
   * @param newDir The directory object you want to add
   */
  public void addChildDirectory(Directory newDir) {
    childrenDirectories.add(newDir);
  }

  /**
   * Add another file object as a child of this directory
   * 
   * @param file The file object you want to add
   */
  public void addChildFile(File file) {
    childrenFiles.add(file);
  }

  /**
   * A method to remove a child file different name
   * 
   * @param file The file object you want to remove,
   * @author Ruyin Zhang
   */
  public void mvChildFile(String filename) {
    int n = this.childrenFiles.size();
    if (n > 0) {
      for (int i = n - 1; i >= 0; i--) {
        if (this.childrenFiles.get(i).getFileName().equals(filename)) {
          this.childrenFiles.remove(i);
        }
      }
    }
  }

  /**
   * A method to remove a child directory
   * 
   * @param dirname , the child directory that you want to remove
   * @author Ruyin Zhang
   */
  public void mvChildDirectory(String dirname) {

    int n = this.childrenDirectories.size();
    if (n > 0) {
      for (int i = n - 1; i >= 0; i--) {
        if (this.childrenDirectories.get(i).getDirectoryName()
            .equals(dirname)) {
          this.childrenDirectories.remove(i);
        }
      }
    }
  }

  /**
   * Get the current name of the directory
   * 
   * @return String The directory name
   */
  public String getDirectoryName() {
    return this.directoryName;
  }

  /**
   * A method to set a new name to a directory
   * 
   * @param dirname, the new name for the current directory
   * @author Ruyin Zhang
   */
  public void setDirectoryName(String dirname) {
    this.directoryName = dirname;
  }

  /**
   * A method to retrieve the object that holds all of the File objects which
   * are children of this directory.
   * 
   * @author Radu Laudat
   * @return ArrayList<File> the ArrayList that holds all children file objects
   *         of this directory.
   */

  public ArrayList<File> getChildFiles() {
    return childrenFiles;
  }

  /**
   * A method to retrieve the object that holds all of the Directory objects
   * which are children of this directory.
   * 
   * @author Jason Ku
   * @return ArrayList<Directory> the ArrayList that holds all children
   *         Directory objects of this directory.
   */
  public ArrayList<Directory> getChildrenDirectories() {
    return childrenDirectories;
  }

  public ArrayList<Object> getAllChildren() {
    ArrayList<Object> returnList = new ArrayList<Object>();
    for (Directory nextDir : childrenDirectories) {
      returnList.add(nextDir);
    }
    for (File nextFile : childrenFiles) {
      returnList.add(nextFile);
    }
    return returnList;
  }

  /**
   * Method to check whether or not a directory/file exists in the path given
   * 
   * @return boolean if exists, true and false is does not exist
   */
  public boolean isChildPath(String path, String fileOrDir) {
    String Name;
    int slashStartIndex;
    // If path is empty, then the directory exists
    if (path.length() == 0) {
      return true;
    } else if (!path.contains("/")
        || (path.indexOf("/") == path.length() - 1)) {
      // Check if path has an extra / at the end
      if (path.indexOf("/") == path.length() - 1) {
        path = path.substring(0, path.length() - 1);
      }
      // Path is a file
      if (fileOrDir.equals("file")) {
        for (File nextFile : this.childrenFiles) {
          // Check if the file is a child
          if (nextFile.getFileName().equals(path)) {
            // Return true since the file is a child
            return true;
          }
        }
      }
      // If the path is ..
      else if (path.equals("..")) {
        return this.getParent().isChildPath("", "directory");
      }
      // If the path is .
      else if (path.equals(".")) {
        return true;
      } else if (fileOrDir.equals("directory")) {
        for (Directory nextDirectory : this.childrenDirectories) {
          // Check if the directory is a child
          if (nextDirectory.getDirectoryName().equals(path)) {
            return true;
          }
        }
      }
    } else {
      // Get the next directory name
      slashStartIndex = path.indexOf("/");
      // If slashStartIndex is -1 then that means this is last directory
      if (slashStartIndex != -1) {
        Name = path.substring(0, slashStartIndex);
        path = path.substring(slashStartIndex + 1);
      } else {
        Name = path;
      }
      if (Name.equals("..")) {
        Directory parent = this.getParent();
        // If parent is not null, then change into the parent
        if (parent != null) {
          return parent.isChildPath(path, fileOrDir);
        } else {
          return false;
        }
      } else if (Name.equals(".")) {
        return this.isChildPath(path, fileOrDir);
      } else {
        // Loop through all children
        for (Directory nextChild : this.childrenDirectories) {
          // Check if the directory is a child
          if (nextChild.getDirectoryName().equals(Name)) {
            // Get the directory and check if path exists again
            return nextChild.isChildPath(path, fileOrDir);
          }
        }
      }
      // No directory has been found as a child so return false
      return false;
    }
    return false;
  }

  /**
   * Method to retrieve a directory if it exists
   * 
   * @param path The absolute, with "/" removed at the 0th index or the relative
   *        path
   * @return Directory Returns the directory object at the given path
   */
  public Directory getPathDirectory(String path) {
    int slashStartIndex;
    String directoryName;
    Directory currDirectory = this;
    ArrayList<String> allDirectories = new ArrayList<String>();
    // Get the directory names and add them to a list
    while (path.length() != 0) {
      slashStartIndex = path.indexOf("/");
      // If slashStartIndex is -1 then that means this is last directory
      if (slashStartIndex != -1) {
        directoryName = path.substring(0, slashStartIndex);
        path = path.substring(slashStartIndex + 1);
        allDirectories.add(directoryName);
      } else {
        allDirectories.add(path);
        path = "";
      }
    }
    // Loop through the path directory names
    for (String nextDirectoryName : allDirectories) {
      // Check if the path is ..
      if (nextDirectoryName.equals("..")) {
        // Get the parent directory
        Directory parent = currDirectory.getParent();
        // Change into it
        currDirectory = parent;
      } else {
        // Find the right directory object
        int i = 0;
        while (i < currDirectory.childrenDirectories.size()) {
          if (currDirectory.childrenDirectories.get(i).getDirectoryName()
              .equals(nextDirectoryName)) {
            currDirectory = currDirectory.childrenDirectories.get(i);
          }
          i++;
        }
      }
    }
    return currDirectory;
  }

  /**
   * Method to retrieve a file if it exists
   * 
   * @param path The absolute, with "/" removed at the 0th index or the relative
   *        path
   * @return File or Null Returns the file object or null if the file was not
   *         found
   */
  public File getPathFile(String path) {
    int slashStartIndex;
    String fileName;
    Directory currDirectory = this;
    ArrayList<String> allDirectories = new ArrayList<String>();
    // Get the directory names and add them to a list
    while (path.length() != 0) {
      slashStartIndex = path.indexOf("/");
      // If slashStartIndex is -1 then that means this is last directory
      if (slashStartIndex != -1) {
        directoryName = path.substring(0, slashStartIndex);
        path = path.substring(slashStartIndex + 1);
        allDirectories.add(directoryName);
      } else {
        allDirectories.add(path);
        path = "";
      }
    }
    // Get the last item in allDirectories since it's a file
    fileName = allDirectories.get(allDirectories.size() - 1);
    // Loop through the path directory names
    for (String nextDirectoryName : allDirectories) {
      if (nextDirectoryName.equals("..")) {
        Directory parent = currDirectory.getParent();
        currDirectory = parent;
      } else {
        // Find the right directory object
        int i = 0;
        while (i < currDirectory.childrenDirectories.size()) {
          if (currDirectory.childrenDirectories.get(i).getDirectoryName()
              .equals(nextDirectoryName)) {
            currDirectory = currDirectory.childrenDirectories.get(i);
          }
          i++;
        }
      }
    }
    // Find the file object
    for (File nextFile : currDirectory.childrenFiles) {
      if (nextFile.getFileName().equals(fileName)) {
        return nextFile;
      }
    }
    return null;
  }

  /**
   * Print all of the children:directories and files of this current directory
   * 
   * @return String of the children names of the directory
   */
  public String printAllChildren() {
    String contents = "";
    // For the directories
    for (Directory nextDir : this.childrenDirectories) {
      contents = contents + nextDir.getDirectoryName() + "\n";
    }
    // For the files
    for (File nextFile : this.childrenFiles) {
      contents = contents + (nextFile.getFileName()) + "\n";
    }
    return contents;
  }

  /**
   * A method to return whether or not a directory is a child already
   * 
   * @param dirName The name of the directory you want to check
   * @return bool True if exists false otherwise
   */
  public boolean dirExists(String dirName) {
    boolean result = false;
    // Loop through the children directories
    for (Directory nextChild : this.childrenDirectories) {
      // Check if the directory exists
      if (nextChild.getDirectoryName().equals(dirName)) {
        result = true;
      }
    }
    return result;
  }

  /**
   * A method to return whether or not a file is a child already
   * 
   * @param fileName The name of the file you want to check
   * @return boolean True if the file exists and false otherwise
   */
  public boolean fileExists(String fileName) {
    boolean result = false;
    // Loop through the children files
    for (File nextFile : this.childrenFiles) {
      if (nextFile.getFileName().equals(fileName)) {
        result = true;
      }
    }
    return result;
  }

  /**
   * A getter method for the parent Directory
   * 
   * @return parent Gets the parent directory
   */
  public Directory getParent() {
    return parent;
  }

  /**
   * A setter method for the parent Directory
   * 
   * @param parent
   */
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * A method that gets the fullPath of the directory
   */
  public String getFullPath() {
    String fullPath = "";
    Directory traverseDir;
    // Loop through the parents
    traverseDir = this.getParent();
    // If the traverseDir is not null, add it
    if (traverseDir != null) {
      // If the traverse Dir is root
      if (this.getDirectoryName().equals("/")) {
        fullPath = this.getDirectoryName();
      } else {
        fullPath = "/" + this.getDirectoryName();
      }
      fullPath = traverseDir.getFullPath() + fullPath;
    }
    return fullPath;
  }

}
