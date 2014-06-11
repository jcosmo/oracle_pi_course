package org.tharsisgate.lesson2.homework;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinListener;

public class Button
{
  private GPIOPin _button;

  public Button( final int port, final int pin, final int trigger )
  {
    try
    {
      _button = DeviceManager.open( new GPIOPinConfig( port,
                                                       pin, GPIOPinConfig.DIR_INPUT_ONLY,
                                                       DeviceConfig.DEFAULT,
                                                       trigger, false ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  public void close()
  {
    if ( null != _button )
    {
      try
      {
        _button.close();
      }
      catch ( IOException e )
      {
        e.printStackTrace();
      }
    }
  }

  public void setInputListener( final PinListener inputListener )
  {
    try
    {
      _button.setInputListener( inputListener );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
}
