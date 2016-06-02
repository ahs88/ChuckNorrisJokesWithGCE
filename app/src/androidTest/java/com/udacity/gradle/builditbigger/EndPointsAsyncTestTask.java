package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shetty on 16/05/16.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class EndPointsAsyncTestTask {
    private static final String TAG = EndPointsAsyncTestTask.class.getName();
    private static final long TIME_OUT = 10;

    boolean isSuccess;
    Context context;
    String joke;


    @Test
    @UiThreadTest
    public final void testJokeEndPoint() throws Throwable {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

                new EndPointsAsyncTask(new JokeEndPointListener() {
                    @Override
                    public void onRequestSuccess() {
                        isSuccess = true;
                        Log.d(TAG, "JokeEndPointListener: on success callback");
                    }

                    @Override
                    public void onRequestFailure() {

                    }

                    @Override
                    public void onResult(String result) {

                    }
                }, context,Constants.BASE_URL) {
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        joke = result;
                        countDownLatch.countDown();
                        Log.d(TAG,"onPostExecute joke:"+result);
                    }
                }.execute();



        countDownLatch.await(TIME_OUT, TimeUnit.SECONDS);
        assertTrue(isSuccess);
        assertNotNull(joke);
    }
}
