package org.tharsisgate.spike.gpio;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;

public class GpioPinTestMIDlet
  extends MIDlet
{
  private GPIOPinTest _pinTest;

  @Override
  protected void startApp()
  {
    System.out.println("test");
    _pinTest = new GPIOPinTest();

    try
    {
      _pinTest.start();
    }
    catch ( Exception e )
    {
      System.out.println("Dying!");
      e.printStackTrace();
      notifyDestroyed();
    }
  }

  @Override
  protected void destroyApp( final boolean b )
  {
    try
    {
      _pinTest.stop();
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
}
