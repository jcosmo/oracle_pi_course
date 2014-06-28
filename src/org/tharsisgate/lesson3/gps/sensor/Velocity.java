package org.tharsisgate.lesson3.gps.sensor;

public class Velocity
{
  private final long _timeStamp;
  private final double _bearing;
  private final double _speed;

  public Velocity( final long timeStamp, final double bearing, final double speed )
  {

    _timeStamp = timeStamp;
    _bearing = bearing;
    _speed = speed;
  }

  public long getTimeStamp()
  {
    return _timeStamp;
  }

  public double getBearing()
  {
    return _bearing;
  }

  public double getSpeed()
  {
    return _speed;
  }

  @Override
  public String toString()
  {
    return "Velocity{" +
           "time=" + _timeStamp +
           ", bearing =" + _bearing +
           ", speed=" + _speed +
           '}';
  }
}
