package com.sohoj_path;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private SearchView searchView;
    private FirebaseAuth auth;
    private List<Place> placeList;
    private PlaceAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView   = view.findViewById(R.id.search_city);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        placeList = new ArrayList<>();
        String userEmail = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "";

        adapter = new PlaceAdapter(placeList, userEmail, new PlaceAdapter.OnPlaceActionListener() {
            @Override
            public void onView(Place place) {
                Toast.makeText(getContext(), "Viewing: " + place.getName(), Toast.LENGTH_SHORT).show();
                ViewPlaceFragment viewFragment = new ViewPlaceFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("place", place);
                viewFragment.setArguments(bundle);

                ((MainActivity) getActivity()).loadFragment(viewFragment, false);

            }

            @Override
            public void onEdit(Place place) {
                Toast.makeText(getContext(), "Start Updating: " + place.getName(), Toast.LENGTH_SHORT).show();
                UpdatePlaceFragment updateFragment = new UpdatePlaceFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("place", place);
                updateFragment.setArguments(bundle);

                ((MainActivity) getActivity()).loadFragment(updateFragment, false);
            }

//            @Override
//            public void onDelete(Place place) {
//                db.collection("places").document(place.getDocumentId())
//                        .delete()
//                        .addOnSuccessListener(aVoid -> {
//                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
//                            placeList.remove(place);
//                            adapter.notifyDataSetChanged();
//                        });
//            }
            @Override
            public void onDelete(Place place) {
            new AlertDialog.Builder(getContext())
            .setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this place?")
            .setPositiveButton("Yes", (dialog, which) -> {
                db.collection("places").document(place.getDocumentId())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            placeList.remove(place);
                            adapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                        });
            })
            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
            .show();
             }


        });

        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { filterByCity(query); return true; }
            @Override public boolean onQueryTextChange(String newText){ filterByCity(newText); return true; }
        });

        loadPlaces();

        return view;
    }

    private void loadPlaces() {
        db.collection("places").orderBy("name")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    placeList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Place place = doc.toObject(Place.class);
                        if (place != null) {
                            place.setDocumentId(doc.getId());
                            placeList.add(place);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to load places.", Toast.LENGTH_SHORT).show());
    }

    private void filterByCity(String query) {
        if (query == null) query = "";
        List<Place> filtered = new ArrayList<>();
        for (Place p : placeList) {
            String city = p.getCity() != null ? p.getCity() : "";
            if (city.toLowerCase().contains(query.toLowerCase()))
                filtered.add(p);
        }
        adapter.setFilteredList(filtered);
    }


}