package com.yauhenav.logic.dto;

public class Subject {
    private int id;
    private String description;

    public Subject(int id) {
        this.id = id;
        description = null;
    }

    public Subject(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String toString() {
        return "[Subject description: " + this.description + ", ID No." + this. id + "]";
    }
}
