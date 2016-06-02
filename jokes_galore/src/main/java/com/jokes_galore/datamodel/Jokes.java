package com.jokes_galore.datamodel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jokes_galore.datamodel.Joke;


@Generated("org.jsonschema2pojo")
public class Jokes {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private Joke value;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The value
     */
    public Joke getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Joke value) {
        this.value = value;
    }

}