package com.example.flutter_complete_guide;

import android.app.Application;
import android.os.Bundle;

import io.flutter.app.FlutterApplication;
import com.evergage.android.ClientConfiguration;
import com.evergage.android.Evergage;

public class MyFlutterApplication extends FlutterApplication {
    public Evergage evergage;


    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Evergage:
        Evergage.initialize(this);
    }


    public void startEvg(String account, String ds){
        evergage = Evergage.getInstance();
        evergage.start(new ClientConfiguration.Builder().account(account).dataset(ds).usePushNotifications(true).build());
    }

}