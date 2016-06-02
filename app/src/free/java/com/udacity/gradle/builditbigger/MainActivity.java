package com.udacity.gradle.builditbigger;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ahs.udacity.jokes_interface.JokesActivity;
import com.ahs.udacity.jokes_interface.JokesConstants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends BaseActivity implements JokeEndPointListener{

    private static final String TAG = MainActivity.class.getName();
    private InterstitialAd mInterstitialAd;
    private boolean resultRecieved;
    private String mJoke;
    private boolean adClosed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        setupInterstitialAd();
        requestNewInterstitial();

    }

    private void setupInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d(TAG,"onAdClosed");
                synchronized (getApplicationContext()) {
                    adClosed = true;
                    requestNewInterstitial();
                    if (resultRecieved) {
                        loadJokeIntent();
                        resultRecieved = false;
                    }
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG,"onAdLoaded");
            }
        });
    }

    private void loadJokeIntent() {
        Log.d(TAG,"loadJokeIntent");
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putExtra(JokesConstants.JOKES_EXTRA,mJoke);
        startActivity(intent);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        /**/
        Log.d(TAG,"tell joke");
        showProgress();
        new EndPointsAsyncTask(this,this,Constants.BASE_URL).execute();//new Pair<Context, String>(this, "Manfred")
        if (mInterstitialAd.isLoaded()) {
            Log.d(TAG,"Interstitial ad loaded");
            mInterstitialAd.show();
        }


    }


    @Override
    public void onRequestSuccess() {
        hideProgress();

    }

    @Override
    public void onRequestFailure() {
        hideProgress();
        Toast.makeText(this,"Request failed. Please try again later",Toast.LENGTH_LONG);
    }

    @Override
    public void onResult(String joke) {
        Log.d(TAG,"onREsult");
        //Toast.makeText(this, joke.getJoke(), Toast.LENGTH_SHORT).show();
        hideProgress();
        synchronized (getApplicationContext()) {
            mJoke = joke;
            resultRecieved = true;
            if (adClosed && !mInterstitialAd.isLoaded()) {
                loadJokeIntent();
                adClosed = false;
            }
        }
    }
}
