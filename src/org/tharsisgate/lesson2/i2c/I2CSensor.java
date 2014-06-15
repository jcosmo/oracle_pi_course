package org.tharsisgate.lesson2.i2c;

import java.io.IOException;
import java.nio.ByteBuffer;
import jdk.dio.ClosedDeviceException;
import jdk.dio.DeviceManager;
import jdk.dio.UnavailableDeviceException;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;

public class I2CSensor
{
  private I2CDevice _device = null;
  private int _i2cBus = 1;
  private int _clockSpeed = 3400000;              // default clock 3.4MHz Max clock
  private int _addressSizeBits = 7;               // default address
  private int _address = 0x00;                    // I2C device address
  private final int _registerSize = 1;            // Register size in bytes

  private ByteBuffer _commandBuffer;

  public I2CSensor( final int address )
    throws IOException
  {
    _address = address;
    connectToDevice();
  }

  public I2CSensor( final int i2cBus, final int address, final int addressSizeBits, final int clockSpeed )
    throws IOException
  {
    _address = address;
    _addressSizeBits = addressSizeBits;
    _clockSpeed = clockSpeed;
    _i2cBus = i2cBus;
    connectToDevice();
  }

  private void connectToDevice()
    throws IOException
  {
    // Construct the shared ByteBuffers
    // Reusing the buffers rather than allocating new space each time is
    // good practice with embedded devices to reduce garbage collection.
    _commandBuffer = ByteBuffer.allocateDirect( _registerSize );
    final I2CDeviceConfig config = new I2CDeviceConfig( _i2cBus, _address, _addressSizeBits, _clockSpeed );
    _device = DeviceManager.open( config );
    System.out.println( "Connected to the device OK!!!" );
  }

  public void writeByte( final int register, final byte byteToWrite )
    throws IOException
  {
    _commandBuffer.clear();
    _commandBuffer.put( byteToWrite );
    _commandBuffer.rewind();
    _device.write( register, _registerSize, _commandBuffer );
  }

  public boolean readBytes( final int register, final int count, final ByteBuffer buffer )
    throws IOException
  {
    buffer.clear();
    final int result = _device.read( register, _registerSize, buffer );
    if ( result != count )
    {
      System.out.println("Failed to read " + count + " bytes.  Got " + result );
      return false;
    }
    return true;
  }
}
