package com.example.krishna.youtube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.krishna.youtube.pojo.Item;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class SecondActivtiy extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    String s=null;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activtiy);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.you);
        youTubePlayerView.initialize("AIzaSyC3lKgzLF2HdauYk4F1o35BjRqNUbOxFBA",this);
        Intent i=getIntent();
         s=i.getExtras().getString("id");




    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(s);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
