package org.tharsisgate.lesson2.bmp180;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;

public class Bmp180MIDlet
  extends MIDlet
{
  private Bmp180Sensor _bmp;

  @Override
  protected void startApp()
  {
    try
    {
      _bmp = new Bmp180Sensor();
    }
    catch ( final IOException e )
    {
      e.printStackTrace();
      notifyDestroyed();
      return;
    }

    new Thread( new Runnable()
    {
      @Override
      public void run()
      {
        while ( true )
        {
          final float temp = _bmp.readTemperature();
          final float pressure = _bmp.readPressure();
          System.out.println( "temp = " + temp );
          System.out.println( "pressure = " + pressure );
          try
          {
            Thread.sleep( 1000 );
          }
          catch ( InterruptedException e )
          {
          }
        }
      }
    } ).start();
  }

  @Override
  protected void destroyApp( final boolean b )
  {
  }
}
