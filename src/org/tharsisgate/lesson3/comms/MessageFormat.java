package org.tharsisgate.lesson3.comms;

import org.tharsisgate.lesson3.gps.sensor.Position;
import org.tharsisgate.lesson3.gps.sensor.Velocity;

public final class MessageFormat
{
  private MessageFormat()
  {
  }

  public static String format( final Position position, final Velocity velocity )
  {
    return "^^" + position + "^" + velocity;
  }
}
