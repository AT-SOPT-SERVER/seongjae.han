package org.sopt.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.sopt.domain.Post;

public class FileRepositoryUtil {

  private static final String WORKSPACE_PATH = "src/main/java/org/sopt/assets";

  public void save(List<Post> posts) {
    try {
      File file = new File(WORKSPACE_PATH, "post.txt");
      FileOutputStream fileOutputStream = new FileOutputStream(file, false);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

      objectOutputStream.writeObject(posts);
      objectOutputStream.close();
      fileOutputStream.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Post> fetchAllPosts() {
    File file = new File(WORKSPACE_PATH, "post.txt");

    if (!file.exists() || file.length() == 0) {
      return new ArrayList<>();
    }

    try (
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fis)
    ) {
      List<Post> deserializedPosts = (List<Post>) in.readObject();
      return new ArrayList<>(deserializedPosts);
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

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
    }
    catch (IOException e) {
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
