package com.rosarycollege.teachbyvoice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rosarycollege.utility.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    public com.rosarycollege.utility.MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new MediaPlayer(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player.isPlaying())
            player.stop();
        player.reset();
        player.release();
    }

    public MediaPlayer getPlayer() {
        return player;
    }
}