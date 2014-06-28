package org.tharsisgate.lesson3.gps.sensor;

import java.io.IOException;

public interface GPSSensor
{
  String getRawData( String type )
    throws IOException;

  Position getPosition()
    throws IOException;

  Velocity getVelocity()
    throws IOException;
}
