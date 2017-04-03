package com.digzdigital.cumessenger;

import android.app.Application;

import com.digzdigital.cumessenger.dagger.AppComponent;
import com.digzdigital.cumessenger.dagger.AppModule;
import com.digzdigital.cumessenger.dagger.DaggerAppComponent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orm.SugarApp;

/**
 * Created by Digz on 09/03/2017.
 */

public class MessengerApplication extends SugarApp {

    private static MessengerApplication instance = new MessengerApplication();
    private static AppComponent appComponent;

    public static MessengerApplication getInstance(){
     return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        getAppComponent();
        FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public AppComponent getAppComponent(){
        if (appComponent ==null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

}
