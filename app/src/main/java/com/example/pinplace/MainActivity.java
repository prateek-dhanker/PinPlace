package com.example.pinplace;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private List<Location> allLocations = new ArrayList<Location>();
    private LocationViewModel locationViewModel;
    public void onGetLocationClicked(View view) {
        fetchCurrentLocation(this);
    }
    public void openNameDescriptionModal(double latitude, double longitude) {
        android.view.LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_location, null);
        android.widget.EditText nameInput = dialogView.findViewById(R.id.editTextName);
        android.widget.EditText descInput = dialogView.findViewById(R.id.editTextDescription);
        android.widget.EditText addressInput = dialogView.findViewById(R.id.editTextAddress);

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Add Location")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = nameInput.getText().toString().trim();
                    String description = descInput.getText().toString().trim();
                    String address = addressInput.getText().toString().trim();

                    if (name.isEmpty()) {
                        name = "New location";
                    }

                    Location newLocation = new Location(latitude, longitude, name, description,address);
                    locationViewModel.insert(newLocation);
                    Toast.makeText(MainActivity.this, "Location added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void fetchCurrentLocation(Context context) {

        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION },
                    1001
            );
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        openNameDescriptionModal(latitude, longitude);
                    } else {
                        Toast.makeText(MainActivity.this, "Location is NULL", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Failed to fetch location, retry", Toast.LENGTH_SHORT).show();
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted → call location function again
                fetchCurrentLocation(this);
            } else {
                Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        locationViewModel.getAllLocations().observe(this, locations -> {
            allLocations.clear();
            allLocations.addAll(locations);
            customAdapter.notifyDataSetChanged();
        });

        customAdapter = new CustomAdapter(this,allLocations);

        recyclerView = findViewById(R.id.allLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }
}