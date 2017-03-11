package com.digzdigital.cumessenger.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Digz on 20/02/2017.
 */

public class User implements Parcelable{
    private String uid;
    private String id;
    private String department;
    private String programme;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private String status;
    private int graduationYear;
    private String phoneNumber;
    private String email;

    public User() {

    }

    public User(Parcel in){
        this.uid = in.readString();
        this.id = in.readString();
        this.department = in.readString();
        this.programme = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.middleName = in.readString();
        this.dateOfBirth = in.readString();
        this.status = in.readString();
        this.graduationYear = in.readInt();
        this.phoneNumber = in.readString();
        this.email = in.readString();

    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(uid);
        parcel.writeString(id);
        parcel.writeString(department);
        parcel.writeString(programme);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(middleName);
        parcel.writeString(dateOfBirth);
        parcel.writeString(status);
        parcel.writeInt(graduationYear);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel parcel){
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }

    };
    @Override
    public int describeContents(){
        return hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGraduationYear() {
        return "Class of " + graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
