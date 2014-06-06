package org.tharsisgate.spike.gpio;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import jdk.dio.ClosedDeviceException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.UnavailableDeviceException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;
import org.tharsisgate.lesson1.timers.TestTask;

public class GPIOPinTest
  implements PinListener
{
  // Emulator Port names
  private static final int LED1_ID = 0;
  private static final int BUTTON_PORT = 0;
  private static final int BUTTON_PIN = 0;
  private GPIOPin _led1;
  private GPIOPin _button1;
  private Timer _timer;

  public void start()
    throws IOException
  {
    // Open the LED pin (Output)
    System.out.println("Opening pin");

    _led1 = (GPIOPin) DeviceManager.open( LED1_ID );

    GPIOPinConfig config1 = new GPIOPinConfig( BUTTON_PORT,
                                               BUTTON_PIN, GPIOPinConfig.DIR_INPUT_ONLY,
                                               DeviceConfig.DEFAULT,
                                               GPIOPinConfig.TRIGGER_RISING_EDGE, false );
    // Open the BUTTON pin (Input)
    _button1 = (GPIOPin) DeviceManager.open( config1 );
    // Add this class as a pin listener to the buttons
    _button1.setInputListener( this );

    // Turn the LED on, then off - this tests the LED
    _led1.setValue( true );
    try
    {
      Thread.sleep( 1000 );
    }
    catch ( InterruptedException ex )
    {
    }
    // Start the LED's off (false)
    _led1.setValue( false );
  }

  public void stop()
    throws IOException
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
    _timer.cancel();
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
    catch ( UnavailableDeviceException e )
    {
      e.printStackTrace();
    }
    catch ( ClosedDeviceException e )
    {
      e.printStackTrace();
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
}
