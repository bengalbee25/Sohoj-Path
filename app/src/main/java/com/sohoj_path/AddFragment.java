package com.sohoj_path;

import android.os.Bundle;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFragment extends Fragment {
    private TextInputEditText editPlaceName, editCity, editAddress, editNotes;
    private Spinner spinnerType, spinnerRating;
    private RadioGroup radioEntrance, radioToilet;
    private Button buttonSubmit;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize Firestore & Auth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Bind views
        editPlaceName = view.findViewById(R.id.edit_place_name);
        editCity = view.findViewById(R.id.edit_city);
        editAddress = view.findViewById(R.id.edit_address);
        editNotes = view.findViewById(R.id.edit_notes);
        spinnerType = view.findViewById(R.id.spinner_type);
        spinnerRating = view.findViewById(R.id.spinner_rating);
        radioEntrance = view.findViewById(R.id.radio_entrance);
        radioToilet = view.findViewById(R.id.radio_toilet);
        buttonSubmit = view.findViewById(R.id.button_submit);

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



        buttonSubmit.setOnClickListener(v -> submitData());

        return view;
    }

    private void submitData() {
        String name = editPlaceName.getText().toString().trim();
        String city = editCity.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String notes = editNotes.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();
        String rating = spinnerRating.getSelectedItem().toString();
        String userEmail = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "Unknown";

        boolean accessibleEntrance = radioEntrance.getCheckedRadioButtonId() == R.id.radio_entrance_yes;
        boolean accessibleToilet = radioToilet.getCheckedRadioButtonId() == R.id.radio_toilet_yes;

        if (name.isEmpty() || city.isEmpty() || address.isEmpty() || type.equals("Select Type") || rating.equals("Select Rating")) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Place place = new Place(name, city, type, address, accessibleEntrance,
                accessibleToilet, rating, notes, userEmail);

        db.collection("places")
                .add(place)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Place added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error adding place", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        editPlaceName.setText("");
        editCity.setText("");
        editAddress.setText("");
        editNotes.setText("");
        spinnerType.setSelection(0);
        spinnerRating.setSelection(0);
        radioEntrance.clearCheck();
        radioToilet.clearCheck();
    }


}