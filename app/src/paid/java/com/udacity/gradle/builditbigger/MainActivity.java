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



public class MainActivity extends BaseActivity implements JokeEndPointListener {

    private static final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);



    }


    private void loadJokeIntent(String mJoke) {
        Log.d(TAG, "loadJokeIntent");
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putExtra(JokesConstants.JOKES_EXTRA, mJoke);
        startActivity(intent);
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

    public void tellJoke(View view) {
        /**/
        Log.d(TAG, "tell joke");
        showProgress();
        new EndPointsAsyncTask(this, this, Constants.BASE_URL).execute();//new Pair<Context, String>(this, "Manfred")


    }


    @Override
    public void onRequestSuccess() {
        hideProgress();

    }

    @Override
    public void onRequestFailure() {
        hideProgress();
        Toast.makeText(this, "Request failed. Please try again later", Toast.LENGTH_LONG);
    }

    @Override
    public void onResult(String joke) {
        Log.d(TAG, "onREsult");
        hideProgress();
        loadJokeIntent(joke);
    }
}
