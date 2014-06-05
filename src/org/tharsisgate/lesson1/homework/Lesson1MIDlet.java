package org.tharsisgate.lesson1.homework;

import java.util.Timer;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Lesson1MIDlet
  extends MIDlet
  implements EventListener<OddIntervalEvent>
{
  private static final long FIVE_MINUTES = 10 * 1000;
  private static final long THIRTY_SECONDS = 2 * 1000;

  private Timer _5MinuteTimer;
  private Timer _30SecondTimer;

  @Override
  protected void startApp()
    throws MIDletStateChangeException
  {
    start5MinuteTimer();
  }

  @Override
  protected void destroyApp( final boolean b )
    throws MIDletStateChangeException
  {
    stopTimers();
  }

  private void start5MinuteTimer()
  {
    final OddIntervalDetector task = new OddIntervalDetector( FIVE_MINUTES );
    task.addListener( this );
    _5MinuteTimer = new Timer();
    _5MinuteTimer.scheduleAtFixedRate( task, timeToNextMark( FIVE_MINUTES ), FIVE_MINUTES );
  }

  private long timeToNextMark( final long interval )
  {
    return interval - System.currentTimeMillis() % interval;
  }

  private void stopTimers()
  {
    if ( null != _5MinuteTimer )
    {
      _5MinuteTimer.cancel();
    }
    if ( null != _30SecondTimer )
    {
      _30SecondTimer.cancel();
    }
  }

  @Override
  public void eventOccurred( final OddIntervalEvent event )
  {
    final RunXTimesTask task = new RunXTimesTask( FIVE_MINUTES / THIRTY_SECONDS );
    task.addListener( new EventListener<TaskCompletedEvent>()
    {
      @Override
      public void eventOccurred( final TaskCompletedEvent event )
      {
        _30SecondTimer.cancel();
        _30SecondTimer = null;
      }
    } );
    _30SecondTimer = new Timer();
    _30SecondTimer.schedule( task, 0, THIRTY_SECONDS );
  }
}
