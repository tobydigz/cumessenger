package com.digzdigital.cumessenger.data.db.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.Date;

// import io.realm.RealmObject;

public class Course extends SugarRecord implements Parcelable{

    private Long id;
    private String courseCode;
    private String courseTitle;
    private String venue;
    private int day;
    private int time;
    private int duration;

    public Course() {

    }


    public Course(Parcel in){
        this.courseCode = in.readString();
        this.courseTitle = in.readString();
        this.venue = in.readString();
        this.day = in.readInt();
        this.time = in.readInt();
        this.duration = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(courseCode);
        parcel.writeString(courseTitle);
        parcel.writeString(venue);
        parcel.writeInt(day);
        parcel.writeInt(time);
        parcel.writeInt(duration);
    }


    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>(){
        @Override
        public Course createFromParcel(Parcel parcel){
            return new Course(parcel);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public int describeContents(){
        return hashCode();
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

    public Integer getDay(){
        return day;
    }
    public String getDayText() {
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
            default:
                dayOfWeek ="lol";
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = (int) duration;
    }

    public Integer getTime() {
        return time;
    }

    public String getStartTime(){
        String header = "";
        switch (time) {
            case 1:
                header = "8:00";
                break;
            case 2:
                header = "9:00";
                break;
            case 3:
                header = "10:00";
                break;
            case 4:
                header = "11:00";
                break;
            case 5:
                header = "12:00";
                break;
            case 6:
                header = "13:00";
                break;
            case 7:
                header = "14:00";
                break;
            case 8:
                header = "15:00";
                break;
            case 9:
                header = "16:00";
                break;
            case 10:
                header = "17:00";
                break;
            case 11:
                header = "18:00";
                break;
            case 12:
                header = "19:00";
                break;
        }
        return header;

    }

    public void setTime(int time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
