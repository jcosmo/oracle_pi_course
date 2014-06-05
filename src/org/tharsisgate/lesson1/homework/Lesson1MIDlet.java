package org.tharsisgate.lesson1.homework;

import java.util.Timer;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Lesson1MIDlet
  extends MIDlet
  implements EventListener<OddMinuteEvent>
{
  private static final long FIVE_MINUTES = 1 * 5 * 1000;
  private static final long FIVE_SECONDS = 1 * 1 * 1000;

  private Timer _5MinuteTimer;
  private Timer _5SecondTimer;

  @Override
  protected void startApp()
    throws MIDletStateChangeException
  {
    System.out.println( "Kicking it off" );
    _5MinuteTimer = new Timer();
    _5SecondTimer = new Timer();

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
    final OddMinuteDetector task = new OddMinuteDetector();
    task.addListener( this );

    // System.out.println("Current time is " + (new Date()) + " and time to next mark is " + timeToNext5MinuteMark() );
    _5MinuteTimer.schedule( task, timeToNextMark( FIVE_MINUTES ), FIVE_MINUTES );
  }

  private long timeToNextMark( final long interval )
  {
    return interval - System.currentTimeMillis() % interval;
  }

  private void stopTimers()
  {
    _5MinuteTimer.cancel();
    _5SecondTimer.cancel();
  }

  @Override
  public void eventOccurred( final OddMinuteEvent event )
  {
    final RunXTimesTask task = new RunXTimesTask( FIVE_MINUTES / FIVE_SECONDS );
    task.addListener( event1 -> _5SecondTimer.cancel() );
    _5SecondTimer.schedule( task, timeToNextMark( FIVE_SECONDS ), FIVE_SECONDS );
  }
}
