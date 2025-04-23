package org.sopt.util;

import java.text.BreakIterator;

public class GraphemeUtil {

  public static int count(String text) {
    BreakIterator it = BreakIterator.getCharacterInstance();
    it.setText(text);
    int count = 0;
    while (it.next() != BreakIterator.DONE) {
      count++;
    }
    return count;
  }
}
