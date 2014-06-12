package org.tharsisgate.lesson2.homework;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

public class Led
{
  private volatile GPIOPin _pin;
  private volatile Thread _blink;

  public Led( final int pin )
    throws IOException
  {
    _pin = DeviceManager.open( new GPIOPinConfig( 0,
                                                  pin, GPIOPinConfig.DIR_OUTPUT_ONLY,
                                                  DeviceConfig.DEFAULT,
                                                  0, false ) );
  }

  public void close()
  {
    try
    {
      off();
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
          for ( int i = 0; i < count * 2; i++ )
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
        }
      }
    } );
    _blink.start();
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
