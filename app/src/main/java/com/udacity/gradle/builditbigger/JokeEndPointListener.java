package com.udacity.gradle.builditbigger;

/**
 * Created by shetty on 16/05/16.
 */
public interface JokeEndPointListener {
    public void onRequestSuccess();
    public void onRequestFailure();

    public void onResult(String result);
}
