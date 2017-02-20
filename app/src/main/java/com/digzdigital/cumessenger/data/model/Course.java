package com.digzdigital.cumessenger.data.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Digz on 20/02/2017.
 */

public class Course {

    private String courseCode;
    private String courseTitle;
    private ArrayList<Date> startTime;
    private ArrayList<Date> endTime;
    private String semester;
    private int Level;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public ArrayList<Date> getStartTime() {
        return startTime;
    }

    public void setStartTime(ArrayList<Date> startTime) {
        this.startTime = startTime;
    }

    public ArrayList<Date> getEndTime() {
        return endTime;
    }

    public void setEndTime(ArrayList<Date> endTime) {
        this.endTime = endTime;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getLevel() {
        return Level + "Level";
    }

    public void setLevel(int level) {
        Level = level;
    }
}
