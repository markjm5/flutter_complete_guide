package com.example.flutter_complete_guide;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import io.flutter.app.FlutterActivity;
import io.flutter.app.FlutterApplication;

import com.evergage.android.Campaign;
import com.evergage.android.CampaignHandler;
import com.evergage.android.ClientConfiguration;
import com.evergage.android.Evergage;
import com.evergage.android.EvergageActivity;
import com.evergage.android.Screen;

public class MyFlutterApplication extends FlutterApplication {
    public Evergage evergage;
    public static Campaign returnCampaign;


    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Evergage:
        Evergage.initialize(this);
    }


    public Evergage startEvg(String account, String ds) {
        evergage = Evergage.getInstance();
        evergage.start(new ClientConfiguration.Builder().account(account).dataset(ds).usePushNotifications(true).build());

        //Screen screen = myEvg.getScreenForActivity(ea);

        return evergage;
    }

    public Screen refreshScreen(Evergage myEvg, FlutterActivity fa, String event) {
        // Evergage track screen view
        Screen screen = myEvg.getScreenForActivity(fa);

        if (screen != null) {
            // If screen is viewing a product:
            //screen.viewItem(new Product("p123"));

            // If screen is viewing a category, like women's merchandise:
            //screen.viewCategory(new Category("Womens"));

            // Or if screen is viewing a tag, like some specific brand:
            //screen.viewTag(new Tag("SomeBrand", Tag.Type.Brand));

            // Or maybe screen isn't related to your catalog:
            screen.trackAction(event);
        }
        return screen;

    }

    public Campaign getCampaign(Evergage myEvg, FlutterActivity fa, String event, Campaign activeCampaign) {


        Screen myScreen = myEvg.getScreenForActivity(fa);

        if (myScreen != null) {
            CampaignHandler handler = new CampaignHandler() {

                public Campaign getReturnCampaign() {
                    return returnCampaign;
                }

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
                        returnCampaign = activeCampaign;
                        //return activeCampaign;
                    } else {

                        // Track the impression for statistics even if the user is in the control group.
                        myScreen.trackImpression(campaign);

                        // Only display the campaign if the user is not in the control group.
                        if (!campaign.isControlGroup()) {
                            // Keep active campaign as long as needed for (re)display and comparison
                            returnCampaign = campaign;
                            //Log.d(TAG, "New active campaign name " + campaign.getCampaignName() +" for target " + campaign.getTarget() + " with data " + campaign.getData());

                            // Display campaign content
                            //May Not need This: TextView featuredProductTextView = (TextView) findViewById(R.id.evergage_in_app_message);

                            //May Not Need This: featuredProductTextView.setText("Our featured product is " + featuredProductName + "!");
                        }
                    }
                }
            };

            // The target string uniquely identifies the expected data schema - here, a featured product:
            myScreen.setCampaignHandler(handler, "featuredProduct");
            // Return details of activeCampaign
            //Log.d(TAG, "New active activeCampaign name " + activeCampaign.getCampaignName() +" for target " + activeCampaign.getTarget() + " with data " + activeCampaign.getData());
        }
        return returnCampaign;

    }
}
