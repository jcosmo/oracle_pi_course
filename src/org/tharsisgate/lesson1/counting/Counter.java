package org.tharsisgate.lesson1.counting;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Counter
  extends Thread
{
  private volatile boolean _shouldRun = true;
  private final int _trigger;
  private final List<CountListener> _listeners;
  private int _count;

  public Counter( final int trigger )
  {
    _trigger = trigger;
    _count = 0;
    _listeners = new ArrayList<>();
  }

  public void addListener( final CountListener l )
  {
    synchronized ( _listeners )
    {
      _listeners.add( l );
    }
  }

  public void removeListener( final CountListener l )
  {
    synchronized ( _listeners )
    {
      _listeners.remove( l );
    }
  }

  public void run()
  {
    while ( _shouldRun )
    {
      if ( _count == _trigger )
      {
        notifyListeners( _count );
        _count = 0;
      }

      _count++;
      System.out.println( "Tick.... " + (_trigger - _count) );

      try
      {
        sleep( 2000 );
      }
      catch ( final InterruptedException ignored )
      {
      }
    }
  }

  private void notifyListeners( final int count )
  {
    synchronized ( _listeners )
    {
      for ( final CountListener listener : _listeners )
      {
        listener.countReached( count );
      }
    }
  }

  public void stop()
  {
    _shouldRun = false;
    interrupt();
  }
}
