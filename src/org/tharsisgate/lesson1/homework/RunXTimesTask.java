package org.tharsisgate.lesson1.homework;

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
    _remaining--;
    if ( _remaining == 0 )
    {
      fireEvent( new TaskCompletedEvent() );
    }
  }
}
