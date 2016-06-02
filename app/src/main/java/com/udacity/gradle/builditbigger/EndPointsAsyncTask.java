package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.shetty.backend.myApi.MyApi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by shetty on 15/05/16.
 */


public class EndPointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = EndPointsAsyncTask.class.getName();
    private static MyApi myApiService = null;
    private Context context;
    JokeEndPointListener jokeEndPointListener;
    String jokeUrl;

    public EndPointsAsyncTask(JokeEndPointListener joke_endpoint_listener,Context context1,String url) {
        this.jokeEndPointListener = joke_endpoint_listener;
        context = context1;
        jokeUrl = url;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(jokeUrl)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });


            // end options for devappserver

            myApiService = builder.build();
        }

        //context = params[0].first;
        //String name = params[0].second;

        try {
            com.shetty.backend.myApi.model.Joke joke = myApiService.getJokes().execute();
            if (joke != null) {
                jokeEndPointListener.onRequestSuccess();
            } else {
                jokeEndPointListener.onRequestFailure();
            }
            return joke.getJoke();

        } catch (IOException e) {
            return e.getMessage();
        }
    }




    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, result);
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        jokeEndPointListener.onResult(result);
    }

}

