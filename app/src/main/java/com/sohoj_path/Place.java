package com.sohoj_path;

import java.io.Serializable;

public class Place implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String city;
    private String type;
    private String address;
    private boolean accessible_entrance;
    private boolean accessible_toilet;
    private String accessibility_rating;
    private String notes;
    private String added_by;
    private String documentId;

    public Place() {

    }

    public Place(String name, String city, String type, String address,
                 boolean accessible_entrance, boolean accessible_toilet,
                 String accessibility_rating, String notes, String added_by) {
        this.name = name;
        this.city = city;
        this.type = type;
        this.address = address;
        this.accessible_entrance = accessible_entrance;
        this.accessible_toilet = accessible_toilet;
        this.accessibility_rating = accessibility_rating;
        this.notes = notes;
        this.added_by = added_by;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean isAccessibleEntrance() { return accessible_entrance; }
    public void setAccessibleEntrance(boolean accessibleEntrance) { this.accessible_entrance = accessibleEntrance; }

    public boolean isAccessibleToilet() { return accessible_toilet; }
    public void setAccessibleToilet(boolean accessibleToilet) { this.accessible_toilet = accessibleToilet; }

    public String getRating() { return accessibility_rating; }
    public void setRating(String rating) { this.accessibility_rating = rating; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getUser() { return added_by; }
    public void setUser(String user) { this.added_by = user; }

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

}
