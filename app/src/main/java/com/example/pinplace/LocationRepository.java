package com.example.pinplace;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationRepository {
    private LocationDao locationDao;
    private LiveData<List<Location>> allLocations;
    private ExecutorService executorService;

    public LocationRepository(Application application) {
        LocationDatabase database = LocationDatabase.getDatabase(application);
        locationDao = database.locationDao();
        allLocations = locationDao.getAllLocations();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    public void insert(Location location) {
        executorService.execute(() -> locationDao.insert(location));
    }

    public void insertAll(List<Location> locations) {
        executorService.execute(() -> locationDao.insertAll(locations));
    }

    public void update(Location location) {
        executorService.execute(() -> locationDao.update(location));
    }

    public void delete(Location location) {
        executorService.execute(() -> locationDao.delete(location));
    }

    public void deleteAll() {
        executorService.execute(() -> locationDao.deleteAll());
    }

    public List<Location> getAllLocationsSync() {
        return locationDao.getAllLocationsSync();
    }
}