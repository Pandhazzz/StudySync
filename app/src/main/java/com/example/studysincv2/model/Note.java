package com.example.studysincv2.model;

public class Note {
    private int id;
    private String title;
    private String description;
    private String date;

    private boolean isSelected; // untuk menandai apakah catatan dipilih
    private boolean isSelectable; // untuk menandai apakah catatan dapat dipilih


    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;

        this.isSelected = false;
        this.isSelectable = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // tambahkan getters dan setters untuk isSelected dan isSelectable
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        isSelectable = selectable;
    }
}

