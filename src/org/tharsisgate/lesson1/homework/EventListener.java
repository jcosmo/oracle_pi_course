package org.tharsisgate.lesson1.homework;

public interface EventListener<T extends Event>
{
  void eventOccurred( T event );
}
