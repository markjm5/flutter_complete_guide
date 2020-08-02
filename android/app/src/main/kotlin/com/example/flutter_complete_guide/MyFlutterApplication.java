package com.example.flutter_complete_guide;

import android.app.Application;
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
        evergage = Evergage.getInstance();
        evergage.setUserId("janedoe");

        evergage.start(new ClientConfiguration.Builder().account("interactionstudio").dataset("mmukherjee_sandbox").usePushNotifications(true).build());
    }
}