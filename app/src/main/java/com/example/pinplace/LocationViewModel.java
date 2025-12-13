package com.example.pinplace;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class LocationViewModel extends AndroidViewModel {
    private LocationRepository repository;
    private LiveData<List<Location>> allLocations;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        allLocations = repository.getAllLocations();
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    public void insert(Location location) {
        repository.insert(location);
    }

    public void insertAll(List<Location> locations) {
        repository.insertAll(locations);
    }

    public void update(Location location) {
        repository.update(location);
    }

    public void delete(Location location) {
        repository.delete(location);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}