package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by shetty on 07/05/16.
 */
public class JokeApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
