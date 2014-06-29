package org.tharsisgate.lesson3.gps;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import org.tharsisgate.lesson3.gps.persist.PersistentStore;
import org.tharsisgate.lesson3.gps.persist.RMSPersistentStore;
import org.tharsisgate.lesson3.gps.sensor.AdaFruitGPSUARTSensor;
import org.tharsisgate.lesson3.gps.sensor.Position;
import org.tharsisgate.lesson3.gps.sensor.Velocity;

import static org.tharsisgate.lesson3.gps.sensor.LogLevel.DEBUG;

public class RecorderMidlet
  extends MIDlet
{
  @Override
  public void startApp()
  {
    System.out.println( "GPS recording Imlet starting" );

    // Experiment with both types of accessing the GPS device
    try (final AdaFruitGPSUARTSensor gps = new AdaFruitGPSUARTSensor(); final PersistentStore store = new RMSPersistentStore(
      "GpsStore" ))
    {
      gps.enableLogging( true );
      gps.setLogLevel( DEBUG );

      /*
      As an example we'll take one reading every second for 5 readings
       */
      do
      {
        final Position p = gps.getPosition();
        final Velocity v = gps.getVelocity();
        store.saveData( p, v );
        System.out.println( "Saved " + store.getRecordCount() + " : Position and velocity: " + "^^" + p + "^" + v );
        Thread.sleep( 1000 );
      } while ( true );
    }
    catch ( final IOException ioe )
    {
      System.out.println( "Recording Midlet: IOException " + ioe.getMessage() );
      ioe.printStackTrace();
    }
    catch ( final InterruptedException ex )
    {
      // Ignore
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

    /* Terminate the Imlet correctly */
    System.out.println( "Recording Midlet finished" );
    notifyDestroyed();
  }

  @Override
  public void destroyApp( boolean unconditional )
  {
    // Nothing to do here, since we are using AutoCloseable to gracefully close connections
  }
}
