package com.jokes_galore;




import com.jokes_galore.datamodel.Joke;
import com.jokes_galore.datamodel.Jokes;
import com.jokes_galore.jokeinterface.JokeInterface;
import com.jokes_galore.service.JokeService;
import com.jokes_galore.util.RetroUtil;

import java.io.IOException;


import retrofit.Callback;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class JokesGalore {

    private static JokesGalore jokesGalore;
    private JokeService jokeService;
    private JokeInterface jokeInterface;

    public static JokesGalore getInstance(){
        if(jokesGalore == null){
            jokesGalore =  new JokesGalore();
            //setupSerializedConfig();
        }
        return jokesGalore;
    }


    public void setJokeInterface(JokeInterface jokeInterface){
        this.jokeInterface = jokeInterface;
    }




    public Joke retrieveRandomJokeAsync(){

        if (jokeService==null) {
            jokeService = RetroUtil.getInstance(true).create(JokeService.class);
        }

        jokeService.getJokes(new Callback<Jokes>(){

            @Override
            public void success(Jokes jokes, Response response) {
                //jokeInterface.receivedJoke(response.getBody().);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        return null;
    }

    public Joke retrieveRandomJoke(){
        if (jokeService==null) {
            jokeService = RetroUtil.getInstance(true).create(JokeService.class);
        }
        Jokes jokes = jokeService.getJokes();
        /*try {
            Jokes jokes = call.execute().body();
            return jokes.getValue();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
        return jokes.getValue();
    }

}
