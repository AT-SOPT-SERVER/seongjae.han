package org.sopt.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PostIdFileUtil {

  private static final String WORKSPACE_PATH = "src/main/java/org/sopt/assets";

  public Integer getGeneratedId() {
    try {
      File file = new File(WORKSPACE_PATH, "post_id.txt");
      BufferedReader fileReader = new BufferedReader(new FileReader(file));
      String result = fileReader.readLine();
      fileReader.close();
      return Integer.parseInt(result);

    } catch (FileNotFoundException e) {
      this.saveGeneratedId(0);
      return 0;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void saveGeneratedId(final Integer generatedId) {
    try {
      File file = new File(WORKSPACE_PATH, "post_id.txt");
      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
      fileWriter.write(String.valueOf(generatedId));
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
