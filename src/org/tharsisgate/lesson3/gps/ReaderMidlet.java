package org.tharsisgate.lesson3.gps;

import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordEnumeration;
import org.tharsisgate.lesson3.gps.persist.RMSPersistentStore;

public class ReaderMidlet
  extends MIDlet
{
  @Override
  public void startApp()
  {
    System.out.println( "RMS reading Imlet starting" );

    try (final RMSPersistentStore store = new RMSPersistentStore( "GpsStore" ))
    {
      final RecordEnumeration enumerator = store.getRecordEnumerator();
      System.out.println( "Record store contains " + enumerator.numRecords() + " records" );
      int i = 0;
      while (enumerator.hasNextElement())
      {
        i++;
        System.out.println("" + i + " : " + store.getRecordAsString( enumerator.nextRecordId() ));
      }
    }
    catch ( final Exception e )
    {
      e.printStackTrace();
    }

    System.out.println( "Reading Midlet finished" );
    notifyDestroyed();
  }

  @Override
  public void destroyApp( boolean unconditional )
  {
    // Nothing to do here, since we are using AutoCloseable to gracefully close connections
  }
}
