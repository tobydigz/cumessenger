package com.digzdigital.cumessenger.data.db.model;


import java.util.ArrayList;

public class RowObject {
    private String rowHeader;
    private ArrayList<Course> courses;

    public String getRowHeader() {
        return rowHeader;
    }

    public void setRowHeader(String rowHeader) {
        this.rowHeader = rowHeader;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
