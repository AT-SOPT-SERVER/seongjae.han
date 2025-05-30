package org.sopt.global.util;

import java.util.Timer;
import java.util.TimerTask;
import org.springframework.stereotype.Component;

@Component
public class TimeIntervalUtil {

  private static final Long TIME_INTERVAL_IN_MILLI = 3000 * 60L;

  private boolean isAvailable = true;
  private final Timer timer = new Timer(true);

  public Boolean isAvailable() {
    return isAvailable;
  }

  // 타이머가 작동중인 경우 isAvailable false, 끝나면 다시 true
  public void startTimer() {
    if (!isAvailable) {
      return;
    }

    TimerTask checkAvailableTimerTask = new TimerTask() {
      @Override
      public void run() {
        isAvailable = true;
      }
    };

    isAvailable = false;
    timer.schedule(checkAvailableTimerTask, TIME_INTERVAL_IN_MILLI);
  }
}
