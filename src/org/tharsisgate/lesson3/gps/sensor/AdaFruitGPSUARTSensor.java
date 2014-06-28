package org.tharsisgate.lesson3.gps.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.channels.Channels;
import jdk.dio.DeviceManager;
import jdk.dio.uart.UART;

public class AdaFruitGPSUARTSensor
  extends AdaFruitGPSSensor
  implements AutoCloseable
{
  private static final int UART_DEVICE_ID = 40;

  private final UART _uart;
  private final BufferedReader _serialBufferedReader;

  public AdaFruitGPSUARTSensor( )
    throws IOException
  {
    super();
    _uart = DeviceManager.open( UART_DEVICE_ID );
    _uart.setBaudRate( 9600 );
    final InputStream inputStream = Channels.newInputStream( _uart );
    final InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
    _serialBufferedReader = new BufferedReader( inputStreamReader );

    System.out.println( "AdaFruit GPS Sensor: DIO API UART opened" );
  }

  /**
   * Get a line of raw data from the GPS sensor
   *
   * @return The complete line of data
   * @throws IOException If there is an IO error
   */
  String readDataLine()
    throws IOException
  {
    do
    {
      final String line = _serialBufferedReader.readLine();
      if (null != line && '$' == line.charAt( 0 ) )
      {
        return line;
      }
    } while(true);
  }

  /**
   * Close the connection to the GPS receiver via the UART
   *
   * @throws IOException If there is an IO error
   */
  @Override
  public void close()
    throws IOException
  {
    _serialBufferedReader.close();
    _uart.close();
  }
}
