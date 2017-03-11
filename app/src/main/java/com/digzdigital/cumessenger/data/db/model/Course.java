package com.digzdigital.cumessenger.data.db.model;


import java.util.Date;

// import io.realm.RealmObject;

public class Course/* extends RealmObject*/{

    private String id;
    private String courseCode;
    private String courseTitle;
    private String venue;
    private int day;
    private Date startTime;
    private Date endTime;
    private int size;

    public Course() {
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDay() {
        String dayOfWeek ="";
        switch (day){
            case 0:
                dayOfWeek = "Monday";
                break;
            case 1:
                dayOfWeek = "Tuesday";
                break;
            case 2:
                dayOfWeek = "Wednesday";
                break;
            case 3:
                dayOfWeek = "Thursday";
                break;
            case 4:
                dayOfWeek = "Friday";
                break;
            case 5:
                dayOfWeek = "Saturday";
                break;
            case 6:
                dayOfWeek = "Sunday";
                break;
        }
        return dayOfWeek;
    }

    public int getDayInt(){
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
