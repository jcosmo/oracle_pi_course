package org.tharsisgate.lesson1.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import org.tharsisgate.lesson1.counting.CountListener;

public class OddMinuteDetector
  extends EventFiringTimerTask<OddMinuteEvent, EventListener<OddMinuteEvent>>
{
  @Override
  public void run()
  {
    // TODO: Danger... seems unlikely we can guarantee to be run at the exact moment of 'minute change'
    System.out.println("Checking to see if current time is odd number of muliples of 5 minutes");
    fireEvent( new OddMinuteEvent() );
  }
}
