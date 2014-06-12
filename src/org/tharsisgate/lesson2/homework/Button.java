package org.tharsisgate.lesson2.homework;

import java.io.IOException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinListener;

public class Button
{
  private final GPIOPin _button;

  public Button( final int port, final int pin, final int trigger )
    throws IOException
  {
    _button = DeviceManager.open( new GPIOPinConfig( port,
                                                     pin, GPIOPinConfig.DIR_INPUT_ONLY,
                                                     DeviceConfig.DEFAULT,
                                                     trigger, false ) );
  }

  public void close()
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

  public GPIOPin getDevice()
  {
    return _button;
  }
}
