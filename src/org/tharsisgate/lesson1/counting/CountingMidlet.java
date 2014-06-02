package org.tharsisgate.lesson1.counting;

import javax.microedition.midlet.MIDlet;

public class CountingMIDlet
  extends MIDlet
  implements CountListener
{
  private Counter _counter;

  @Override
  protected void startApp()
  {
    System.out.println( "Light it up" );
    _counter = new Counter(5);
    _counter.addListener( this );
    _counter.start();
  }

  @Override
  protected void destroyApp( final boolean b )
  {
    System.out.println( "Boom" );
    _counter.removeListener( this );
    _counter.stop();
  }

  @Override
  public void countReached( final int count )
  {
    System.out.println( "Time to duck... " + count );
  }
}
