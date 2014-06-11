package org.tharsisgate.lesson2.homework;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import jdk.dio.gpio.GPIOPinConfig;
import jdk.dio.gpio.PinEvent;
import jdk.dio.gpio.PinListener;

public class DoorAlertMidlet
  extends MIDlet
  implements PinListener
{
  private Led _greenLed;
  private Led _redLed;
  private Button _button;

  @Override
  protected void destroyApp( final boolean b )
    throws MIDletStateChangeException
  {
    if ( null != _greenLed )
    {
      _greenLed.close();
    }
    if ( null != _redLed )
    {
      _redLed.close();
    }
    if ( null != _button )
    {
      _button.close();
    }
  }

  @Override
  protected void startApp()
    throws MIDletStateChangeException
  {
    _greenLed = new Led( 23 );
    _redLed = new Led( 22 );
    _button = new Button( 25, 0, GPIOPinConfig.TRIGGER_BOTH_EDGES );
    _button.setInputListener( this );
  }

  @Override
  public void valueChanged( final PinEvent pinEvent )
  {
    if ( _button == pinEvent.getDevice() )
    {
      if ( pinEvent.getValue() )
      {
        doorOpened();
      }
      else
      {
        doorClosed();
      }
    }
  }

  private void doorClosed()
  {
    _redLed.off();
    _greenLed.on();
  }

  private void doorOpened()
  {
    _greenLed.off();
    _redLed.blinkThenOn( 3, 500 );
  }
}
