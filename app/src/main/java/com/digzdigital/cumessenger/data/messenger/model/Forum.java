package com.digzdigital.cumessenger.data.messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.digzdigital.cumessenger.data.db.model.User;

import java.util.ArrayList;

/**
 * Created by Digz on 08/03/2017.
 */

public class Forum implements Parcelable{

    private String id;
    private String title;

    private Forum(){

    }

    public Forum(Parcel in){
        this.id = in.readString();
        this.title = in.readString();

    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(id);
        parcel.writeString(title);
    }

    public static final Parcelable.Creator<Forum> CREATOR = new Parcelable.Creator<Forum>(){
        @Override
        public Forum createFromParcel(Parcel parcel){
            return new Forum(parcel);
        }

        @Override
        public Forum[] newArray(int size) {
            return new Forum[size];
        }

    };
    @Override
    public int describeContents(){
        return hashCode();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
