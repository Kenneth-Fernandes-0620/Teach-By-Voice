package com.rosarycollege.teachbyvoice;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rosarycollege.utility.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    static com.rosarycollege.utility.MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar bar = findViewById(R.id.customtoolbar);
        setSupportActionBar(bar);
        player = new MediaPlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public static MediaPlayer getPlayer(){
        return player;
    }
}