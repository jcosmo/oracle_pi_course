package org.tharsisgate.lesson1.homework;

import java.util.Timer;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.tharsisgate.lesson1.timers.TestTask;

public class Lesson1MIDlet
  extends MIDlet
  implements EventListener<OddMinuteEvent>
{
  private Timer _5MinuteTimer;

  @Override
  protected void startApp()
    throws MIDletStateChangeException
  {
    System.out.println( "Kicking it off" );
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
    _5MinuteTimer = new Timer();
    OddMinuteDetector task = new OddMinuteDetector();
    task.addListener( this );

    // TODO: Actually start and repeat at correct schedule
    _5MinuteTimer.schedule( task, 0, 5 );
  }

  private void stopTimers()
  {
    if ( null != _5MinuteTimer )
    {
      _5MinuteTimer.cancel();
    }
  }

  @Override
  public void eventOccurred( final OddMinuteEvent event )
  {
    System.out.println("Odd minute detected!");
    // TODO: kick off 5 second regular timer
  }
}
