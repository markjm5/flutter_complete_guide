package com.example.flutter_complete_guide;

import android.app.Application;

import com.evergage.android.ClientConfiguration;
import com.evergage.android.Evergage;

public class MyApplication extends Application {
    public Evergage evergage;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Evergage:
        Evergage.initialize(this);
        evergage = Evergage.getInstance();

        evergage.start(
                new ClientConfiguration.Builder()
                        .account("interactionstudio")
                        .dataset("mmukherjee_sandbox")
                        .usePushNotifications(true)
                        .build()
        );
    }
}