package org.sopt.global.util;


import com.ibm.icu.text.BreakIterator;
import org.springframework.stereotype.Component;

@Component
public class GraphemeUtil {

  public static int count(String text) {
    BreakIterator breakIterator = BreakIterator.getCharacterInstance();
    breakIterator.setText(text);
    int count = 0;
    while (breakIterator.next() != BreakIterator.DONE) {
      count++;
    }

    return count;
  }
}
