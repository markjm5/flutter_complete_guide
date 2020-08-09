package com.example.flutter_complete_guide;

import android.os.Bundle;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodCall;

import com.evergage.android.Evergage;
import com.evergage.android.EvergageActivity;
import com.evergage.android.ClientConfiguration;

import java.util.Map;


public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "demo.flutter_complete_guide/info";
    private FlutterActivity thisActivity = this;
    private MyFlutterApplication myApp;
    private Evergage myEvg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(new MethodChannel.MethodCallHandler(){
           @Override
           public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {

               Map<String, Object> arguments = methodCall.arguments();

                if(methodCall.method.equals("getMessage")){

                    String account = (String) arguments.get("account");
                    String ds = (String) arguments.get("ds");

                    if (myApp == null) {
                        myApp = new MyFlutterApplication();
                    }

                    if(myEvg == null) {
                        myEvg = myApp.startEvg(account, ds);
                    }

                    myApp.refreshScreen(myEvg, thisActivity);

                    //myApp.onCreate();
                }
           }
        });
    }
}
