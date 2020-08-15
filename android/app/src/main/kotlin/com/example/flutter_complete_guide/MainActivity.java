package com.example.flutter_complete_guide;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodCall;

import com.evergage.android.Evergage;
import com.evergage.android.CampaignHandler;
import com.evergage.android.Campaign;
import com.evergage.android.EvergageActivity;
import com.evergage.android.ClientConfiguration;
import com.evergage.android.Screen;

import java.util.Map;


public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "demo.flutter_complete_guide/info";
    private FlutterActivity thisActivity = this;
    private MyFlutterApplication myApp;
    private Evergage myEvg;
    private Screen myScreen;
    private Campaign activeCampaign;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(new MethodChannel.MethodCallHandler(){
           @Override
           public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {

               Map<String, Object> arguments = methodCall.arguments();

                if(methodCall.method.equals("androidInitialize")){

                    String account = (String) arguments.get("account");
                    String ds = (String) arguments.get("ds");

                    if (myApp == null) {
                        myApp = new MyFlutterApplication();
                    }

                    if(myEvg == null) {
                        myEvg = myApp.startEvg(account, ds);
                    }
                    String message = "Initialized!!";
                    result.success(message);

                }

               if(methodCall.method.equals("androidLogEvent")) {

                   String event = (String) arguments.get("event");
                   myScreen = myApp.refreshScreen(myEvg, thisActivity, event);
                   String message = "Event Logged!!";
                   result.success(message);
               }

               if(methodCall.method.equals("androidGetDataCampaign")) {

                   String event = (String) arguments.get("event");
                   activeCampaign = myApp.getDataCampaign(myEvg, thisActivity, event, activeCampaign);
                   String message = "";
                   if(activeCampaign != null) {
                       message = "New active activeCampaign name " + activeCampaign.getCampaignName() + " for target " + activeCampaign.getTarget() + " with data " + activeCampaign.getData();
                   }else{
                       message = "Campaign is NULL";
                   }
                   result.success(message);
               }
           }
        });
    }
}
