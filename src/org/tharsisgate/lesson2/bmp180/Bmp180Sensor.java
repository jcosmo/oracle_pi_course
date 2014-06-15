package org.tharsisgate.lesson2.bmp180;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.tharsisgate.lesson2.i2c.I2CSensor;

public class Bmp180Sensor
  extends I2CSensor
{
  private static final int WAIT_TIME = 5;
  private static final int CTRL_REG = 0xF4;
  private static final int TEMP_REG = 0xF6;
  private static final int PRESSURE_REG = 0xF8;
  private static final byte CMD_READ_TEMP = (byte) 0x2E;
  private static final byte CMD_READ_PRESSURE = (byte) 0x34;

  // Temperature Calibration fields
  private short AC1;
  private short AC2;
  private short AC3;
  private int AC4;
  private int AC5;
  private int AC6;
  private short B1;
  private short B2;
  private short MB;
  private short MC;
  private short MD;

  private ByteBuffer _tempReadBuffer;
  private ByteBuffer _pressureReadBuffer;

  public Bmp180Sensor()
    throws IOException
  {
    super( 0x77 );
    calibrate();
    _tempReadBuffer = ByteBuffer.allocateDirect( 2 );
    _pressureReadBuffer = ByteBuffer.allocateDirect( 3 );
  }

  private void calibrate()
    throws IOException
  {
    // Read all of the calibration data
    final int calibrationByteCount = 22;
    final ByteBuffer calibrationData = ByteBuffer.allocateDirect( calibrationByteCount );
    final int EEPROM_start = 0xAA;
    if ( !readBytes( EEPROM_start, 22, calibrationData ) )
    {
      throw new RuntimeException( "Unable to read calibration data" );
    }
    // Read each of the pairs of data as a signed short
    calibrationData.rewind();
    AC1 = calibrationData.getShort();
    AC2 = calibrationData.getShort();
    AC3 = calibrationData.getShort();

    // Unsigned short values
    AC4 = readUnsignedShort( calibrationData );
    AC5 = readUnsignedShort( calibrationData );
    AC6 = readUnsignedShort( calibrationData );

    // Signed sort values
    B1 = calibrationData.getShort();
    B2 = calibrationData.getShort();
    MB = calibrationData.getShort();
    MC = calibrationData.getShort();
    MD = calibrationData.getShort();
  }

  private int readUnsignedShort( final ByteBuffer byteBuffer )
  {
    byte[] data = new byte[ 2 ];
    byteBuffer.get( data );
    return ( ( ( data[ 0 ] << 8 ) & 0xFF00 ) + ( data[ 1 ] & 0xFF ) );
  }

  public float readTemperature()
  {
    try
    {
      issueCommand( CMD_READ_TEMP, WAIT_TIME );
      if ( readBytes( TEMP_REG, 2, _tempReadBuffer ) )
      {
        // Calculate the actual temperature
        _tempReadBuffer.rewind();
        final int X1 = ( ( readUnsignedShort( _tempReadBuffer ) - AC6 ) * AC5 ) >> 15;
        final int X2 = ( MC << 11 ) / ( X1 + MD );
        final int B5 = X1 + X2;
        float celsius = ( ( B5 + 8 ) >> 4 ) / 10.0f;
        return celsius;
      }
    }
    catch ( final IOException e )
    {
      e.printStackTrace();
    }
    return 0;
  }

  private void issueCommand( final byte command, final int waitTime )
    throws IOException
  {
    writeByte( CTRL_REG, command );
    try
    {
      Thread.sleep( waitTime );
    }
    catch ( InterruptedException e )
    {
      e.printStackTrace();
    }
  }

  public float readPressure()
  {
    return 0;
  }
}
