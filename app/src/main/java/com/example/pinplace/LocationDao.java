package com.example.pinplace;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

// DAO (Data Access Object)
@Dao
public interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Location> locations);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);

    @Query("SELECT * FROM locations")
    LiveData<List<Location>> getAllLocations();

    @Query("SELECT * FROM locations")
    List<Location> getAllLocationsSync();

    @Query("DELETE FROM locations")
    void deleteAll();

    @Query("DELETE FROM locations WHERE id = :locationId")
    void deleteLocation(int locationId);

    @Query("SELECT COUNT(*) FROM locations")
    int getLocationCount();
}