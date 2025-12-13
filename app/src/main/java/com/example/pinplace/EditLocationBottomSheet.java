package com.example.pinplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class EditLocationBottomSheet extends BottomSheetDialogFragment {
    private TextInputEditText editName, editDescription, editAddress;
    private Button btnSave;
    private Location location;
    private OnLocationUpdatedListener listener;

    public interface OnLocationUpdatedListener {
        void onLocationUpdated(Location location);
    }

    public static EditLocationBottomSheet newInstance(Location location, OnLocationUpdatedListener listener) {
        EditLocationBottomSheet sheet = new EditLocationBottomSheet();
        sheet.location = location;
        sheet.listener = listener;
        return sheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_bottom_sheet, container, false);

        editName = view.findViewById(R.id.sheetEditName);
        editDescription = view.findViewById(R.id.sheetEditDescription);
        editAddress = view.findViewById(R.id.sheetEditAddress);
        btnSave = view.findViewById(R.id.btnSheetSave);

        // Pre-fill data
        if (location != null) {
            editName.setText(location.getName());
            editDescription.setText(location.getDescription());
            editAddress.setText(location.getAddress());
        }

        btnSave.setOnClickListener(v -> saveChanges());

        return view;
    }

    private void saveChanges() {
        String name = editName.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Name is required");
            return;
        }

        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);

        if (listener != null) {
            listener.onLocationUpdated(location);
        }

        dismiss();
    }
}
