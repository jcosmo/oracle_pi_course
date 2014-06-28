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

  public long getTimeStamp()
  {
    return _timeStamp;
  }

  public double getLatitude()
  {
    return _latitude;
  }

  public char getLatitudeDirection()
  {
    return _latitudeDirection;
  }

  public double getLongitude()
  {
    return _longitude;
  }

  public char getLongitudeDirection()
  {
    return _longitudeDirection;
  }

  public double getAltitude()
  {
    return _altitude;
  }

  @Override
  public String toString()
  {
    return "Position{" +
           "time=" + _timeStamp +
           ", lat=" + _latitude +_latitudeDirection +
           ", long=" + _longitude + _longitudeDirection +
           ", alt=" + _altitude +
           '}';
  }
}
