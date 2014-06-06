package org.tharsisgate.spike.gpio;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;

public class GpioBlinkTestMIDlet
  extends MIDlet
{
  private GPIOBlinkTest _test;

  @Override
  protected void startApp()
  {
    System.out.println("test");
    _test = new GPIOBlinkTest();

    try
    {
      _test.start();
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
      _test.stop();
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
}
