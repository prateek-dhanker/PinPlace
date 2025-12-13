package com.example.pinplace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Location> savedLocations = new ArrayList<Location>();
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView image,name,description;
        public final LinearLayout locationCard;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            image = (TextView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            locationCard = (LinearLayout) view.findViewById(R.id.locationCard);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CustomAdapter(Context context, List<Location> dataSet) {
        savedLocations = dataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.locations_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Location location = savedLocations.get(position);
        String colorCode = String.format("#%02X%02X%02X",
                (int)(Math.random()*128),
                (int)(Math.random()*128),
                (int)(Math.random()*128));

        viewHolder.image.setBackgroundColor(Color.parseColor(colorCode));

        viewHolder.image.setText(location.getName().substring(0,1));
        viewHolder.description.setText(location.getDescription());
        viewHolder.name.setText(location.getName());

        viewHolder.locationCard.setOnClickListener(view ->{
            Intent it = new Intent(context, LocationActivity.class);
            it.putExtra("latitude", location.getLatitude());
            it.putExtra("longitude",location.getLongitude());
            it.putExtra("name",location.getName());
            it.putExtra("description",location.getDescription());
            context.startActivity(it);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return savedLocations.size();
    }
}