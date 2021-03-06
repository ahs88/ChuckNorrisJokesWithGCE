/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.shetty.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.jokes_galore.JokesGalore;
import com.jokes_galore.datamodel.Joke;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.shetty.com",
                ownerName = "backend.shetty.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "getJokes")
    public Joke getJokes() { //@Named("name") String name
        /*MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;*/

        JokesGalore jokesGalore = JokesGalore.getInstance();
        return jokesGalore.retrieveRandomJoke();
    }

}
