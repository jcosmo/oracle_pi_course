package org.tharsisgate.lesson1.threads;

public class TestThread
  extends Thread
{
  private volatile boolean _shouldRun = true;

  @Override
  public void run()
  {
    while ( _shouldRun )
    {
      System.out.println( "Doing stuffness!" );
      try
      {
        sleep( 2000 );
      }
      catch ( final InterruptedException ignored )
      {
      }
    }
  }

  public void stop()
  {
    _shouldRun = false;
    interrupt();
  }
}
