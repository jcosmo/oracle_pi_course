package org.tharsisgate.lesson1.threads;

import javax.microedition.midlet.MIDlet;

public class TestMIDlet
  extends MIDlet
{
  private TestThread _testThread;

  @Override
  protected void startApp()
  {
    System.out.println( "Hello world" );
    _testThread = new TestThread();
    _testThread.start();
  }

  @Override
  protected void destroyApp( final boolean b )
  {
    System.out.println( "goodbye world" );
    _testThread.stop();
  }
}
