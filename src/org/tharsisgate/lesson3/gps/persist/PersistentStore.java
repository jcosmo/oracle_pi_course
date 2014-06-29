package org.tharsisgate.lesson3.gps.persist;

import java.io.IOException;
import org.tharsisgate.lesson3.gps.sensor.Position;
import org.tharsisgate.lesson3.gps.sensor.Velocity;

public interface PersistentStore
  extends AutoCloseable
{
  /**
   * Save GPS data: position and velocity
   *
   * @param position The position data to save
   * @param velocity The velocity data to save
   * @return The number of the record saved
   * @throws IOException If there is an IO error
   */
  public int saveData( Position position, Velocity velocity )
    throws IOException;

  /**
   * Get the record count
   *
   * @return The number of records in the persistent store
   */
  public int getRecordCount();
}