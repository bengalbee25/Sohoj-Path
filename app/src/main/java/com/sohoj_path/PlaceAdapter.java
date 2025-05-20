package com.sohoj_path;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{
    private List<Place> placeList;
    private String currentUserEmail;
    private OnPlaceActionListener listener;

    public interface OnPlaceActionListener {
        void onView(Place place);
        void onEdit(Place place);
        void onDelete(Place place);
    }

    public PlaceAdapter(List<Place> placeList, String currentUserEmail, OnPlaceActionListener listener) {
        this.placeList = placeList;
        this.currentUserEmail = currentUserEmail;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.city.setText("City: " + place.getCity());
        holder.type.setText("Type: " + place.getType());
        holder.rating.setText("Rating: " + place.getRating());

        holder.buttonView.setOnClickListener(v -> listener.onView(place));

        if (place.getUser() != null && place.getUser().equals(currentUserEmail)) {
            holder.buttonEdit.setVisibility(View.VISIBLE);
            holder.buttonDelete.setVisibility(View.VISIBLE);
            holder.buttonEdit.setOnClickListener(v -> listener.onEdit(place));
            holder.buttonDelete.setOnClickListener(v -> listener.onDelete(place));
        } else {
            holder.buttonEdit.setVisibility(View.GONE);
            holder.buttonDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void setFilteredList(List<Place> newList) {
        this.placeList = newList;
        notifyDataSetChanged();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView placeName, city, type, rating;
        Button buttonView, buttonEdit, buttonDelete;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.text_place_name);
            city = itemView.findViewById(R.id.text_city);
            type = itemView.findViewById(R.id.text_type);
            rating = itemView.findViewById(R.id.text_rating);
            buttonView = itemView.findViewById(R.id.button_view);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }

}
