package org.tharsisgate.lesson1.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public abstract class EventFiringTimerTask<E extends Event, T extends EventListener<E>>
  extends TimerTask
{
  private final List<T> _listeners = new ArrayList<>();

  public void addListener( final T l )
  {
    synchronized ( _listeners )
    {
      _listeners.add( l );
    }
  }

  public void removeListener( final T l )
  {
    synchronized ( _listeners )
    {
      _listeners.remove( l );
    }
  }

  public void fireEvent( final E event )
  {
    for ( final T listener : _listeners )
    {
      listener.eventOccurred( event );
    }
  }
}
