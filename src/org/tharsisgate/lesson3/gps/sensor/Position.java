package org.tharsisgate.lesson3.gps.sensor;

public class Position
{
  private final long _timeStamp;
  private final double _latitude;
  private final char _latitudeDirection;
  private final double _longitude;
  private final char _longitudeDirection;
  private final double _altitude;

  public Position( final long timeStamp,
                   final double latitude,
                   final char latitudeDirection,
                   final double longitude,
                   final char longitudeDirection, final double altitude )
  {

    _timeStamp = timeStamp;
    _latitude = latitude;
    _latitudeDirection = latitudeDirection;
    _longitude = longitude;
    _longitudeDirection = longitudeDirection;
    _altitude = altitude;
  }

  @Override
  public String toString()
  {
    return String.valueOf( _timeStamp ) +
           ',' +
           _latitude +
           ',' +
           _latitudeDirection +
           ',' +
           _longitude +
           ',' +
           _longitudeDirection +
           ',' +
           _altitude;
  }
}
