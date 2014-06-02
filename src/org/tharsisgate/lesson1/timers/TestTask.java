package org.tharsisgate.lesson1.timers;

import java.util.TimerTask;

public class TestTask
  extends TimerTask
{
  @Override
  public void run()
  {
    System.out.println("Tick...");
  }
}
