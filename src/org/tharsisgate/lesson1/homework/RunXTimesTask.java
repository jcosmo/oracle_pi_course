package org.tharsisgate.lesson1.homework;

import java.util.Date;

public class RunXTimesTask
  extends EventFiringTimerTask<TaskCompletedEvent, EventListener<TaskCompletedEvent>>
{
  private long _remaining;

  public RunXTimesTask( final long count )
  {
    _remaining = count;
  }

  @Override
  public void run()
  {
    System.out.println( "Listening... " + new Date() );
    _remaining--;
    if ( _remaining == 0 )
    {
      fireEvent( new TaskCompletedEvent() );
    }
  }
}
