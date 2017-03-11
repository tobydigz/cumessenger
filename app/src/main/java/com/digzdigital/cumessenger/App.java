package com.digzdigital.cumessenger;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Digz on 09/03/2017.
 */

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
