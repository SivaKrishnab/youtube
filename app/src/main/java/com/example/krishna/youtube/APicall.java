package com.example.krishna.youtube;

import com.example.krishna.youtube.pojo.example;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Krishna on 10/12/2017.
 */

public interface APicall {
    @GET("search?part=snippet&&key=AIzaSyC3lKgzLF2HdauYk4F1o35BjRqNUbOxFBA")
    Call<example> getJSON(@Query("maxResults") int val,@Query("q") String key);
}
