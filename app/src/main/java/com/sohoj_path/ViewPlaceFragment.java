package com.sohoj_path;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewPlaceFragment extends Fragment {

    private TextView tvName, tvCity, tvType, tvAddress, tvEntrance, tvToilet, tvRating, tvNotes, tvAddedBy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_place, container, false);

        tvName     = view.findViewById(R.id.tv_name);
        tvCity     = view.findViewById(R.id.tv_city);
        tvType     = view.findViewById(R.id.tv_type);
        tvAddress  = view.findViewById(R.id.tv_address);
        tvEntrance = view.findViewById(R.id.tv_entrance);
        tvToilet   = view.findViewById(R.id.tv_toilet);
        tvRating   = view.findViewById(R.id.tv_rating);
        tvNotes    = view.findViewById(R.id.tv_notes);
        tvAddedBy  = view.findViewById(R.id.tv_added_by);

        if (getArguments() != null) {
            Place place = (Place) getArguments().getSerializable("place");
            if (place != null) {
                tvName.setText(place.getName());
                tvCity.setText(place.getCity());
                tvType.setText(place.getType());
                tvAddress.setText(place.getAddress());
                tvEntrance.setText((place.isAccessibleEntrance() ? "Yes" : "No"));
                tvToilet.setText((place.isAccessibleToilet() ? "Yes" : "No"));
                tvRating.setText(place.getRating());
                tvNotes.setText(place.getNotes());
                tvAddedBy.setText(place.getUser());
            }
        }

        return view;
    }
}