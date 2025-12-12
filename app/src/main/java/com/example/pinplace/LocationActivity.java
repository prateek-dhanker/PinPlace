package com.example.pinplace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LocationActivity extends AppCompatActivity {
    public TextView tvDesc, tvName, tvLat, tvLng;
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
        tvDesc = findViewById(R.id.tvDesc);
        tvName = findViewById(R.id.tvName);
        tvLat = findViewById(R.id.tvLat);
        tvLng = findViewById(R.id.tvLng);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0.00);
        longitude = intent.getDoubleExtra("longitude", 0.00);
        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");

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

}