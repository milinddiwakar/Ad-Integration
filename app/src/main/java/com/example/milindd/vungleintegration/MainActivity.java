package com.example.milindd.vungleintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.vungle.warren.Vungle;
import com.vungle.warren.AdConfig;              // Custom ad configurations
import com.vungle.warren.InitCallback;          // Initialization callback
import com.vungle.warren.LoadAdCallback;        // Load ad callback
import com.vungle.warren.PlayAdCallback;        // Play ad callback
import com.vungle.warren.VungleNativeAd;        // Flex-Feed ad
import com.vungle.warren.Vungle.Consent;        // GDPR consent
import com.vungle.warren.error.VungleException; // onError message

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "VungleInt";
    final String app_id = "5bee2e4c3667554b81cb69d9";
    private final String autocachePlacementReferenceID = "DEFAULT-7778184";
    private final List<String> placementsList =
            Arrays.asList(autocachePlacementReferenceID, "VINT-3594453", "VFLEXFEED-7539262","VFLEX-1352558");
    private AdConfig adConfig = new AdConfig();
    private RelativeLayout flexfeed_container;
    private VungleNativeAd vungleNativeAd;
    private View nativeAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onInit(){
        Vungle.init(app_id, getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "InitCallback - onSuccess");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(LOG_TAG, "InitCallback - onError: " + throwable.getLocalizedMessage());
                Toast myToast = Toast.makeText(getBaseContext(),"Opps could not initialize vungle SDK",Toast.LENGTH_LONG);
                myToast.show();
            }

            @Override
            public void onAutoCacheAdAvailable(final String placementReferenceID) {
                Log.d(LOG_TAG, "InitCallback - onAutoCacheAdAvailable" +
                        "\n\tPlacement Reference ID = " + placementReferenceID);
                // SDK will request auto cache placement ad immediately upon initialization
                // This callback is triggered every time the auto-cached placement is available
                // This is the best place to add your own listeners and propagate them to any UI logic bearing class
                //setButtonState(defaultAd, true);
            }
        });

    }

    public void onButtonTap(View v){
        Toast myToast = Toast.makeText(getBaseContext(),"Application Id is : " + app_id + " and Placement " + autocachePlacementReferenceID + " is loaded " ,Toast.LENGTH_LONG);
        myToast.show();
        onInit();
    }
    public void onPlayAd(View v){
        if (Vungle.isInitialized()) {

            if (Vungle.isInitialized() && Vungle.canPlayAd(autocachePlacementReferenceID)) {
                // Play default placement with ad customization
                adConfig.setBackButtonImmediatelyEnabled(true);
                adConfig.setAutoRotate(true);
                adConfig.setMuted(false);
                // Optional settings for rewarded ads
                Vungle.setIncentivizedFields("TestUser","RewardedTitle","RewardedBody","RewardedKeepWatching","RewardedClose");

                Vungle.playAd(autocachePlacementReferenceID, adConfig, vunglePlayAdCallback);
            }
        }
    }
    private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
        @Override
        public void onAdStart(final String placementReferenceID) {
            Log.d(LOG_TAG, "PlayAdCallback - onAdStart" +
                    "\n\tPlacement Reference ID = " + placementReferenceID);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final int index = placementsList.indexOf(placementReferenceID);

                }
            });
        }

        @Override
        public void onAdEnd(final String placementReferenceID, final boolean completed, final boolean isCTAClicked) {
            Log.d(LOG_TAG, "PlayAdCallback - onAdEnd" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tView Completed = " + completed + "" +
                    "\n\tDownload Clicked = " + isCTAClicked);
        }

        @Override
        public void onError(final String placementReferenceID, Throwable throwable) {
            Log.d(LOG_TAG, "PlayAdCallback - onError" +
                    "\n\tPlacement Reference ID = " + placementReferenceID +
                    "\n\tError = " + throwable.getLocalizedMessage());
        }
    };

}
