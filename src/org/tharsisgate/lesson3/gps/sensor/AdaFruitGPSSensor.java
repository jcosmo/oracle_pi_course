package org.tharsisgate.lesson3.gps.sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AdaFruitGPSSensor
  implements GPSSensor
{
  private static final String POSITION_TAG = "GPGGA";
  private static final String VELOCITY_TAG = "GPVTG";

  private boolean _logEnabled = false;
  private LogLevel _messageLevel = LogLevel.DATA;

  /**
   * Get a line of raw data from the GPS sensor
   *
   * @return The complete line of data
   * @throws IOException If there is an IO error
   */
  abstract String readDataLine()
    throws IOException;

  /**
   * Get the current position
   * Will block until a valid position is obtained
   *
   * @return The position data
   * @throws IOException If there is an IO error
   */
  @Override
  public Position getPosition()
    throws IOException
  {
    do
    {
      final List<String> fields = readDataFields( POSITION_TAG, 14 );

      /* Record a time stamp for the reading */
      final Date now = new Date();
      final long timeStamp = now.getTime() / 1000;

      try
      {
        final double latitude = ddmmConvert( Double.parseDouble( fields.get( 1 ) ) );
        final double longitude = ddmmConvert( Double.parseDouble( fields.get( 3 ) ) );
        final double altitude = Double.parseDouble( fields.get( 8 ) );
        final char latitudeDirection = fields.get( 2 ).charAt( 0 );
        final char longitudeDirection = fields.get( 4 ).charAt( 0 );

        /* Return the encapsulated data */
        return new Position( timeStamp, latitude, latitudeDirection,
                             longitude, longitudeDirection, altitude );
      }
      catch ( final Exception e )
      {
        log( "Failed parsing position data: " + e.getMessage(), LogLevel.DEBUG );
      }
    } while ( true );
  }

  private double ddmmConvert( final double ddmm )
  {
    final int dd = (int) ( ddmm / 100 );
    final double mm = ddmm % 100.0;
    return dd + mm / 60.0d;
  }

  /**
   * Get the current velocity
   * Will block until a valid velocity is obtained
   *
   * @return The velocity data
   * @throws IOException If there is an IO error
   */
  @Override
  public Velocity getVelocity()
    throws IOException
  {
    do
    {
      final List<String> fields = readDataFields( VELOCITY_TAG, 9 );

      /* Record a time stamp for the reading */
      final Date now = new Date();
      final long timeStamp = now.getTime() / 1000;

      try
      {
        final double bearing = Double.parseDouble( fields.get( 0 ) );
        final double speed = Double.parseDouble( fields.get( 6 ) );

        return new Velocity( timeStamp, bearing, speed );
      }
      catch ( final Exception e )
      {
        log( "Failed parsing position data: " + e.getMessage(), LogLevel.DEBUG );
      }
    } while ( true );
  }

  /**
   * Turn on or off logging messaging
   *
   * @param enable Whether to enable logging messages
   */
  public void enableLogging( final boolean enable )
  {
    _logEnabled = enable;
  }

  /**
   * Set the level of messages to display, 1 = ERROR, 2 = INFO
   *
   * @param level The level for messages
   */
  public void setLogLevel( final LogLevel level )
  {
    _messageLevel = level;
  }

  /**
   * Get a string of the right type, of raw data from the GPS receiver.
   * Will block until such a string is found
   *
   * @param type The type of data to be retrieved
   * @return A line of data for that type
   * @throws IOException If there is an IO error
   */
  @Override
  public String getRawData( String type )
    throws IOException
  {
    do
    {
      final String dataLine = readDataLine();
      if ( dataLine.startsWith( "$" + type + "," ) )
      {
        return dataLine.substring( type.length() + 2 );
      }
    } while ( true );
  }

  private List<String> readDataFields( final String dataType, final int expectedFieldCount )
    throws IOException
  {
    do
    {
      final String rawData = getRawData( dataType );
      final List<String> fields = new ArrayList<>( expectedFieldCount );
      final int fieldCount = splitCSVString( rawData, fields );
      if ( expectedFieldCount != fieldCount )
      {
        log( "Invalid position string, contained " + fieldCount + " fields instead of 14: " + rawData, LogLevel.DEBUG );
      }
      else
      {
        return fields;
      }
    } while ( true );
  }

  /**
   * Break a comma separated value string into its individual fields. We need to
   * have this as explicit code because Java ME does not support String.split or
   * java.util.regex and StringTokenizer has a bug that affects empty fields.
   *
   * @param input The CSV input string
   * @return The number of fields extracted
   */
  private int splitCSVString( final String input, final List<String> fields )
  {
    fields.clear();
    int start = 0;
    int end;

    while ( ( end = input.indexOf( ",", start ) ) != -1 )
    {
      fields.add( input.substring( start, end ) );
      start = end + 1;
    }
    if ( start < input.length() )
    {
      fields.add( input.substring( start ) );
    }

    return fields.size();
  }

  /**
   * Print a message if appropriate level of logging is turned on
   *
   * @param message The message to print
   * @param level   Message level
   */
  protected void log( final String message, final LogLevel level )
  {
    if ( _logEnabled && level.ordinal() <= _messageLevel.ordinal() )
    {
      System.out.println( "AdaFruit GPS Sensor: " + message );
    }
  }
}
