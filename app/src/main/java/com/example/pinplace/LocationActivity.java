package com.example.pinplace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LocationActivity extends AppCompatActivity {
    private LocationViewModel locationViewModel;
    public TextView tvDesc, tvName, tvLat, tvLng;
    Location location;
    Double latitude, longitude;
    String name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        tvDesc = findViewById(R.id.tvDesc);
        tvName = findViewById(R.id.tvName);
        tvLat = findViewById(R.id.tvLat);
        tvLng = findViewById(R.id.tvLng);

        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        name = location.getName();
        description = location.getDescription();

        tvDesc.setText(description);
        tvName.setText(name);
        tvLat.setText("Latitude: " + latitude.toString());
        tvLng.setText("Longitude: " + longitude.toString());

    }
    private void openInGoogleMapsView(Context context, double latitude, double longitude, String label) {
        String uriString = "geo:" + latitude + "," + longitude + "?q="
                + latitude + "," + longitude + "(" + label + ")";
        Uri gmmIntentUri = Uri.parse(uriString);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        try {
            context.startActivity(mapIntent);
        } catch (Exception e) {
            // Fallback to browser if Google Maps is not installed
            Uri webUri = Uri.parse("https://maps.google.com/?q=" + latitude + "," + longitude);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            context.startActivity(webIntent);
        }
    }
    public void onClickMaps(View view){
        openInGoogleMapsView(this, latitude, longitude, name);
    }

    public void deleteLocation(View view){
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Location")
                .setMessage("Are you sure you want to delete '" + location.getName() + "'?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    locationViewModel.delete(location);
                    Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateUI(Location updatedLocation){
        tvDesc.setText(updatedLocation.getDescription());
        tvName.setText(updatedLocation.getName());
    }
    public void editLocation(View view) {
        EditLocationBottomSheet bottomSheet = EditLocationBottomSheet.newInstance(
                location,
                updatedLocation -> {
                    // Update in database
                    locationViewModel.update(updatedLocation);
                    updateUI(updatedLocation);
                    Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
                }
        );
        bottomSheet.show(getSupportFragmentManager(), "EditLocationBottomSheet");
    }

}