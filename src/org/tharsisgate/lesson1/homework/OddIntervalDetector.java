package org.tharsisgate.lesson1.homework;

import java.util.Date;

public class OddIntervalDetector
  extends EventFiringTimerTask<OddIntervalEvent, EventListener<OddIntervalEvent>>
{
  private final long _interval;

  public OddIntervalDetector( final long intervalInMilliseconds )
  {
    _interval = intervalInMilliseconds;
  }

  @Override
  public void run()
  {
    System.out.println( "Time: " + new Date() );

    // Trigger if now is an odd number of intervals
    final long now = System.currentTimeMillis() / _interval;
    if ( now % 2 == 1 )
    {
      fireEvent( new OddIntervalEvent() );
    }
  }
}
