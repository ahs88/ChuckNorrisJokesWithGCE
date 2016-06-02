package com.jokes_galore.util;




import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import retrofit.GsonConverterFactory;
import retrofit.RestAdapter;
//import retrofit.Retrofit;


/**
 * Created by akshath on 11/30/2015.
 */
public class RetroUtil implements HttpRequestInterceptor {


    public static RestAdapter retrofit;
    public static boolean currentStatus;
    private static final String BASE_URL = " \t\n" +
            "http://api.icndb.com/";

    public static RestAdapter getInstance(boolean converter) {

        if(retrofit == null || currentStatus != converter) {
            //OkHttpClient client = new OkHttpClient();
            /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(interceptor);*/
            if(converter) {
                retrofit = new RestAdapter.Builder().setEndpoint(BASE_URL)
                        .build();//.client(client)//.baseUrl(BASE_URL)//.addConverterFactory(GsonConverterFactory.create())
            }
            else
            {
                retrofit = new RestAdapter.Builder().setEndpoint(BASE_URL)
                        .build();//.client(client)//.addConverterFactory(GsonConverterFactory.create())//.baseUrl(BASE_URL)
            }
            currentStatus = converter;
        }
        return retrofit;

    }

    public static String retrieveValueFromJson(String info,String valueTag) {
        String data="";
        try {
            JSONObject jsonObject = new JSONObject(info);
            data = (String)jsonObject.get(valueTag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;

    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {

    }
}
