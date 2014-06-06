package org.tharsisgate.spike.gpio;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.midlet.MIDlet;
import jdk.dio.ClosedDeviceException;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.UnavailableDeviceException;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;
import org.tharsisgate.lesson1.timers.TestTask;

public class GPIOBlinkTest
{
  // Emulator Port names
  private static final int LED1_ID = 23;
  private GPIOPin _led1;
  private Timer _timer;

  public void start()
    throws IOException
  {
    System.out.println("Opening pin");

    _led1 = (GPIOPin) DeviceManager.open( LED1_ID );

    _timer = new Timer();
    _timer.schedule( new TimerTask() {
      public void run()
      {
        System.out.println("Tick...");
        try
        {
          _led1.setValue( !_led1.getValue() );
        }
        catch ( IOException e )
        {
          e.printStackTrace();
        }
      }

    }, 0, 1000 );

    // Start the LED's off (false)
    _led1.setValue( false );
  }

  public void stop()
    throws IOException
  {
    _timer.cancel();
    if ( _led1 != null )
    {
      _led1.setValue( false );
      _led1.close();
    }
  }
}
