package com.example.pinplace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.Objects;

public class LocationActivity extends AppCompatActivity {
    private LocationViewModel locationViewModel;
    public TextView tvDesc, tvName, tvLat, tvLng, tvAddress;
    Location location;
    Double latitude, longitude;
    String name, description, address;

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
        tvAddress = findViewById(R.id.tvAddress);
        tvLat = findViewById(R.id.tvLat);
        tvLng = findViewById(R.id.tvLng);

        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        name = location.getName();

        boolean emptyDesc = Objects.equals(location.getDescription(), "");
        boolean emptyAddress = Objects.equals(location.getAddress(), "");
        description = emptyDesc ? "Add description" : location.getDescription();
        address = emptyAddress ? "Add address" : location.getAddress();

        tvDesc.setText(description);
        tvName.setText(name);
        tvLat.setText("Latitude: " + latitude.toString());
        tvLng.setText("Longitude: " + longitude.toString());
        tvAddress.setText(address);

        if(emptyDesc)
            tvDesc.setTextColor(Color.GRAY);
        else
            tvDesc.setTextColor(Color.WHITE);
        if(emptyAddress)
            tvAddress.setTextColor(Color.GRAY);
        else
            tvDesc.setTextColor(Color.WHITE);
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
    public void shareLocation(View view) {
        // Create the location text to share with PinPlace branding
        String shareText = "📍 Shared from PinPlace\n\n" +
                "Location: " + location.getName() + "\n" +
                "Coordinates: " + location.getLatitude() + ", " + location.getLatitude() + "\n\n" +
                "View on map: https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude() + "&label=" + location.getName() + "\n\n" +
                "Get PinPlace to save and share your favorite locations!";

        // Create share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Location from PinPlace");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        // Create chooser to let user select app
        Intent chooser = Intent.createChooser(shareIntent, "Share location via");

        // Verify that the intent will resolve to an activity
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
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
        String name = updatedLocation.getName();

        boolean emptyDesc = Objects.equals(updatedLocation.getDescription(), "");
        boolean emptyAddress = Objects.equals(updatedLocation.getAddress(), "");

        String description = emptyDesc ? "Add description" : updatedLocation.getDescription();
        String address = emptyAddress ? "Add address" : updatedLocation.getAddress();

        tvDesc.setText(description);
        tvName.setText(name);
        tvAddress.setText(address);

        if(emptyDesc)
            tvDesc.setTextColor(Color.GRAY);
        else
            tvDesc.setTextColor(Color.WHITE);
        if(emptyAddress)
            tvAddress.setTextColor(Color.GRAY);
        else
            tvAddress.setTextColor(Color.WHITE);
    }
    public void editLocation(View view) {
        EditLocationBottomSheet bottomSheet = EditLocationBottomSheet.newInstance(
                location,
                updatedLocation -> {
                    // Update in database
                    locationViewModel.update(updatedLocation);
                    location = updatedLocation;
                    updateUI(updatedLocation);
                    Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
                }
        );
        bottomSheet.show(getSupportFragmentManager(), "EditLocationBottomSheet");
    }

}