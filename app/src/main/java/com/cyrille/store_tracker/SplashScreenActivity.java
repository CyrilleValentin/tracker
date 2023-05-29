package com.cyrille.store_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;



public class SplashScreenActivity extends AppCompatActivity {
    public static int SPLASH_TIMER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        VideoView videoView = findViewById(R.id.video);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.vid;
        videoView.setVideoURI(Uri.parse(videoPath));

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
      //  videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });


        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIMER);


    }
}