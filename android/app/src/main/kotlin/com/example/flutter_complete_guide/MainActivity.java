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

                }

               if(methodCall.method.equals("androidLogEvent")) {

                   String event = (String) arguments.get("event");
                   myScreen = myApp.refreshScreen(myEvg, thisActivity, event);

               }

               if(methodCall.method.equals("androidGetDataCampaign")) {

                   String event = (String) arguments.get("event");
                   if (myScreen != null) {
                       CampaignHandler handler = new CampaignHandler() {
                           @Override
                           public void handleCampaign(@NonNull Campaign campaign) {
                               // Validate the campaign data since it's dynamic JSON. Avoid processing if fails.
                               String featuredProductName = campaign.getData().optString("featuredProductName");
                               if (featuredProductName == null || featuredProductName.isEmpty()) {
                                   return;
                               }

                               // Check if the same content is already visible/active (see Usage Details above).
                               if (activeCampaign != null && activeCampaign.equals(campaign)) {
                                   //Log.d(TAG, "Ignoring campaign name " + campaign.getCampaignName() + " since equivalent content is already active");
                                   return;
                               }

                               // Track the impression for statistics even if the user is in the control group.
                               myScreen.trackImpression(campaign);

                               // Only display the campaign if the user is not in the control group.
                               if (!campaign.isControlGroup()) {
                                   // Keep active campaign as long as needed for (re)display and comparison
                                   activeCampaign = campaign;
                                   //Log.d(TAG, "New active campaign name " + campaign.getCampaignName() +" for target " + campaign.getTarget() + " with data " + campaign.getData());

                                   // Display campaign content
                                   //May Not need This: TextView featuredProductTextView = (TextView) findViewById(R.id.evergage_in_app_message);

                                   //May Not Need This: featuredProductTextView.setText("Our featured product is " + featuredProductName + "!");
                               }
                           }
                       };

                       // The target string uniquely identifies the expected data schema - here, a featured product:
                       myScreen.setCampaignHandler(handler, "featuredProduct");

                       // Return details of activeCampaign
                       //Log.d(TAG, "New active activeCampaign name " + activeCampaign.getCampaignName() +" for target " + activeCampaign.getTarget() + " with data " + activeCampaign.getData());
                       String message = "New active activeCampaign name " + activeCampaign.getCampaignName() +" for target " + activeCampaign.getTarget() + " with data " + activeCampaign.getData();
                       result.success(message);
                   }
               }


           }
        });
    }
}
