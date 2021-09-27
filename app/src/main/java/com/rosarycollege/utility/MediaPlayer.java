package com.rosarycollege.utility;

import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MediaPlayer extends android.media.MediaPlayer {
    private static final String TAG = "MediaPlayer";
    android.media.MediaPlayer player;
    private String link = "";
    private File file;

    public MediaPlayer(){
        player = new android.media.MediaPlayer();
        player.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build());
        player.setOnPreparedListener(mediaPlayer -> player.start());
    }

    void controlMediaPlayer(Context context, File file){
        if(player.isPlaying()) {
            player.pause();
        }
        else if(file.equals(this.file)){
            player.start();
        }
        else {
            try{
                player.setDataSource(context, Uri.fromFile(file));
                player.prepareAsync();
            }
            catch (IOException io){
                Log.e(TAG, "controlMediaPlayer: ",io );
            }
            this.file = file;
            Log.d(TAG, "controlMediaPlayer: "+this.link);
        }
    }

    void controlMediaPlayer(Uri link){
        // Starting stopping Audio
        if(player.isPlaying()) {
            player.pause();
        }
        else if(link.toString().equals(this.link)){
            player.start();
        }
        else {
            try{
                player.setDataSource(String.valueOf(link));
                player.prepareAsync();
            }
            catch (IOException io){
                Log.e(TAG, "controlMediaPlayer: ",io );
            }
            this.link = link.toString();
            Log.d(TAG, "controlMediaPlayer: "+this.link);
        }
    }
}
