package com.rosarycollege.teachbyvoice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rosarycollege.utility.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    static com.rosarycollege.utility.MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new MediaPlayer();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static MediaPlayer getPlayer(){
        return player;
    }
}