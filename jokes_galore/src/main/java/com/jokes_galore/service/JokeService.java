package com.jokes_galore.service;

import com.jokes_galore.datamodel.Joke;
import com.jokes_galore.datamodel.Jokes;

import javax.security.auth.callback.Callback;


import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;


/**
 * Created by shetty on 08/05/16.
 */
public interface JokeService {
    static final String API_KEY = "iS0QSS0bjsmshVPhuC3Vk44ReaPrp1IphLwjsns1aoT7nhxnC1";
    String JOKE_SEARCH = "/jokes/random";


    /*@Headers("X-Mashape-Key:"+API_KEY)*/
    @GET(JOKE_SEARCH)
    Jokes getJokes();

    @GET(JOKE_SEARCH)
    void getJokes(retrofit.Callback<Jokes> callback);

}
