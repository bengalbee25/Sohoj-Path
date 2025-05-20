package com.sohoj_path;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;


public class UpdatePlaceFragment extends Fragment {
    private TextInputEditText editPlaceName, editCity, editAddress, editNotes;
    private Spinner spinnerType, spinnerRating;
    private RadioGroup radioEntrance, radioToilet;
    private Button buttonUpdate;

    private FirebaseFirestore db;
    private Place place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_place, container, false);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();

        // Get passed Place object
        if (getArguments() != null) {
            place = (Place) getArguments().getSerializable("place");
        }

        // Link views
        editPlaceName = view.findViewById(R.id.edit_place_name);
        editCity = view.findViewById(R.id.edit_city);
        editAddress = view.findViewById(R.id.edit_address);
        editNotes = view.findViewById(R.id.edit_notes);
        spinnerType = view.findViewById(R.id.spinner_type);
        spinnerRating = view.findViewById(R.id.spinner_rating);
        radioEntrance = view.findViewById(R.id.radio_entrance);
        radioToilet = view.findViewById(R.id.radio_toilet);
        buttonUpdate = view.findViewById(R.id.button_update);

        // Set up Spinners
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Select Type", "Medical", "Educational", "Commercial", "Other"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Select Rating", "Low", "Moderate", "High"});
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(ratingAdapter);

        // Pre-fill data
        if (place != null) {
            editPlaceName.setText(place.getName());
            editCity.setText(place.getCity());
            editAddress.setText(place.getAddress());
            editNotes.setText(place.getNotes());

            // Set spinner selections
            spinnerType.setSelection(typeAdapter.getPosition(place.getType()));
            spinnerRating.setSelection(ratingAdapter.getPosition(place.getRating()));

            // Set radio button selections
            radioEntrance.check(place.isAccessibleEntrance() ? R.id.radio_entrance_yes : R.id.radio_entrance_no);
            radioToilet.check(place.isAccessibleToilet() ? R.id.radio_toilet_yes : R.id.radio_toilet_no);
        }

        buttonUpdate.setOnClickListener(v -> updatePlace());

        return view;
    }

    private void updatePlace() {
        String name = editPlaceName.getText().toString().trim();
        String city = editCity.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String notes = editNotes.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();
        String rating = spinnerRating.getSelectedItem().toString();
        boolean accessibleEntrance = radioEntrance.getCheckedRadioButtonId() == R.id.radio_entrance_yes;
        boolean accessibleToilet = radioToilet.getCheckedRadioButtonId() == R.id.radio_toilet_yes;

        if (name.isEmpty() || city.isEmpty() || address.isEmpty() || type.equals("Select Type") || rating.equals("Select Rating")) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (place == null || place.getDocumentId() == null) {
            Toast.makeText(getContext(), "Invalid place data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update values
        place.setName(name);
        place.setCity(city);
        place.setAddress(address);
        place.setNotes(notes);
        place.setType(type);
        place.setRating(rating);
        place.setAccessibleEntrance(accessibleEntrance);
        place.setAccessibleToilet(accessibleToilet);

        db.collection("places")
                .document(place.getDocumentId())
                .set(place)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Place updated successfully", Toast.LENGTH_SHORT).show();
                    // Replace with HomeFragment manually
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.framelayout, new HomeFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to update place", Toast.LENGTH_SHORT).show();
                });
    }
}