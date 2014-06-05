package org.tharsisgate.lesson1.homework;

import java.util.Date;

public class OddMinuteDetector
  extends EventFiringTimerTask<OddMinuteEvent, EventListener<OddMinuteEvent>>
{
  private final long FIVE_MINUTES = 10 * 1000;

  @Override
  public void run()
  {
    System.out.println( "Time: " + new Date() );
    // Truncate to last timing trigger event, in case of timing/delay issues
    final long now = System.currentTimeMillis() / FIVE_MINUTES;

    // Trigger if now is an odd number of 5 minutes
    if ( now % 2 == 1 )
    {
      fireEvent( new OddMinuteEvent() );
    }
  }
}
