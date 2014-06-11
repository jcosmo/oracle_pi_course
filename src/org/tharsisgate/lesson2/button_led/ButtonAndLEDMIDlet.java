package org.tharsisgate.lesson2.button_led;

import java.io.IOException;
import java.util.Timer;
import javax.microedition.midlet.MIDlet;
import jdk.dio.ClosedDeviceException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.UnavailableDeviceException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;
import org.tharsisgate.spike.gpio.GPIOPinTest;

public class ButtonAndLEDMIDlet
  extends MIDlet
  implements PinListener
{
  // Values for emulator
  //private static final int LED1_PIN = 2;
  //private static final int BUTTON_PORT = 0;
  //private static final int BUTTON_PIN = 0;

  // Values for PI
  private static final int LED1_PIN = 23;
  private static final int BUTTON_PORT = 0;
  private static final int BUTTON_PIN = 17;

  private GPIOPin _led1;
  private GPIOPin _button1;

  @Override
  protected void startApp()
  {
    try
    {
      _led1 = (GPIOPin) DeviceManager.open( LED1_PIN );

      GPIOPinConfig config1 = new GPIOPinConfig( BUTTON_PORT,
                                                 BUTTON_PIN, GPIOPinConfig.DIR_INPUT_ONLY,
                                                 DeviceConfig.DEFAULT,
                                                 GPIOPinConfig.TRIGGER_BOTH_EDGES, false );
      _led1.setValue( true );
      _button1 = DeviceManager.open( config1 );
      _button1.setInputListener( this );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  @Override
  protected void destroyApp( final boolean b )
  {
    try
    {
      if ( _led1 != null )
      {
        _led1.setValue( false );
        _led1.close();
      }
      if ( _button1 != null )
      {
        _button1.close();
      }
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }

  @Override
  public void valueChanged( final PinEvent pinEvent )
  {
    final GPIOPin pin = pinEvent.getDevice();
    try
    {
      if ( pin == _button1 )
      {
        System.out.println("Setting led1");
        _led1.setValue( !_led1.getValue() );
      }
    }
    catch ( final UnavailableDeviceException e )
    {
      e.printStackTrace();
    }
    catch ( final ClosedDeviceException e )
    {
      e.printStackTrace();
    }
    catch ( final IOException e )
    {
      e.printStackTrace();
    }
  }
}
