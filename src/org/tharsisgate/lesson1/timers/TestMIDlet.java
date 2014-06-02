package org.tharsisgate.lesson1.timers;

import java.util.Timer;
import javax.microedition.midlet.MIDlet;

public class TestMIDlet
  extends MIDlet
{
  private Timer _timer;

  @Override
  protected void startApp()
  {
    System.out.println( "Light it up" );
    _timer = new Timer();
    final TestTask task = new TestTask();
    _timer.schedule( task, 0, 2000 );
  }

  @Override
  protected void destroyApp( final boolean b )
  {
    System.out.println( "Boom" );
    _timer.cancel();
  }
}
