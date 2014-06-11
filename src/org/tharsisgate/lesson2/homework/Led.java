package org.tharsisgate.lesson2.homework;

import java.io.IOException;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;

public class Led
{
  private final GPIOPin _pin;
  private volatile Thread _blink;

  public Led( final int pin )
  {
    _pin = DeviceManager.open( pin );
  }

  public void close()
  {
    try
    {
      _pin.close();
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  public void off()
  {
    stopBlinking();
    try
    {
      _pin.setValue( false );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  public void on()
  {
    stopBlinking();
    try
    {
      _pin.setValue( true );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  public void blinkThenOn( final int count, final int duration )
  {
    stopBlinking();
    _blink = new Thread( new Runnable()
    {
      @Override
      public void run()
      {
        try
        {
          _pin.setValue( true );
          for (int i = 0; i < count * 2; i++ )
          {
            Thread.sleep( duration );
            _pin.setValue( !_pin.getValue() );
          }
        }
        catch ( final IOException e )
        {
          e.printStackTrace();
        }
        catch ( final InterruptedException e )
        {
          e.printStackTrace();
        }
      }
    });
  }

  private void stopBlinking()
  {
    if ( null != _blink )
    {
      _blink.interrupt();
      _blink = null;
    }
  }
}
