package com.example.flutter_complete_guide;

import android.os.Bundle;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodCall;

import com.evergage.android.Evergage;
import com.evergage.android.ClientConfiguration;


public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "demo.flutter_complete_guide/info";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(new MethodChannel.MethodCallHandler(){
           @Override
           public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                if(methodCall.method.equals("getMessage")){
                    String message = "Android says hi.";
                    result.success(message);

                    MyFlutterApplication myApp = new MyFlutterApplication();

                    //myApp.onCreate();
                }
           }
        });
    }
}
