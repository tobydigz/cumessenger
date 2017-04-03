package com.digzdigital.cumessenger.dagger;

import com.digzdigital.cumessenger.MessengerApplication;
import com.digzdigital.cumessenger.data.AppDataManager;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.db.AppDbHelper;
import com.digzdigital.cumessenger.data.db.DbHelper;
import com.digzdigital.cumessenger.data.messenger.ApiHelper;
import com.digzdigital.cumessenger.data.messenger.AppApiHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final MessengerApplication app;

    public AppModule(MessengerApplication app){
        this.app = app;
    }


    @Provides @Singleton
    public DataManager providesDataManager(){
        return new AppDataManager();
    }

    @Provides @Singleton
    public ApiHelper providesApiHelper(){
        return new AppApiHelper();
    }

    @Provides @Singleton
    public DbHelper providesDbHelper(){
        return new AppDbHelper();
    }
}
