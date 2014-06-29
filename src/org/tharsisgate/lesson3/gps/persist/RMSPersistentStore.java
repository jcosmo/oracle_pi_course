package org.tharsisgate.lesson3.gps.persist;

import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
import org.tharsisgate.lesson3.comms.MessageFormat;
import org.tharsisgate.lesson3.gps.sensor.Position;
import org.tharsisgate.lesson3.gps.sensor.Velocity;

public class RMSPersistentStore
  implements PersistentStore
{
  final String _storeName;
  final RecordStore _store;

  public RMSPersistentStore( final String storeName )
    throws RecordStoreException
  {
    _storeName = storeName;
    _store = RecordStore.openRecordStore( _storeName, true );
  }

  @Override
  public int saveData( final Position position, final Velocity velocity )
    throws IOException
  {
    final byte[] bytes = MessageFormat.format( position, velocity ).getBytes();
    try
    {
      _store.addRecord( bytes, 0, bytes.length );
      return _store.getNumRecords();
    }
    catch ( final RecordStoreException e )
    {
      throw new IOException( e );
    }
  }

  @Override
  public int getRecordCount()
  {
    try
    {
      return _store.getNumRecords();
    }
    catch ( RecordStoreNotOpenException e )
    {
      return 0;
    }
  }

  @Override
  public void close()
    throws Exception
  {
    _store.close();
  }
}
