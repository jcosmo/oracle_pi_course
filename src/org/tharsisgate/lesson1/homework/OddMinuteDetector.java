package org.tharsisgate.lesson1.homework;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import org.tharsisgate.lesson1.counting.CountListener;

public class OddMinuteDetector
  extends EventFiringTimerTask<OddMinuteEvent, EventListener<OddMinuteEvent>>
{
  private final long FIVE_MINUTES = 5*1000;

  @Override
  public void run()
  {
    System.out.println("Listening...");
    // Truncate to last timing trigger event, in case of timing/delay issues
    final long now = System.currentTimeMillis() / FIVE_MINUTES;

    // Trigger if now is an odd number of 5 minutes
    if (now % 2 == 1)
    {
      fireEvent( new OddMinuteEvent() );
    }
  }
}
