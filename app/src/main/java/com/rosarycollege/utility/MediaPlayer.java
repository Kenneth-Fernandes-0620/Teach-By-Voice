package com.rosarycollege.utility;

import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MediaPlayer extends android.media.MediaPlayer {
    private static final String TAG = "MediaPlayer";
    android.media.MediaPlayer player;
    private int currentId = -1;
    private PlayToPauseMorphingImageView currentRefs;
    private final Context context;

    public MediaPlayer(Context context) {
        player = new android.media.MediaPlayer();
        player.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build());
        player.setOnPreparedListener(mediaPlayer -> player.start());
        player.setOnErrorListener((mediaPlayer, i, i1) -> {
            Log.d(TAG, "onError: " + i + "" + i1);
            return false;
        });
        this.context = context;
//        player.setOnErrorListener(err-> Toast.makeText());
        currentRefs = new PlayToPauseMorphingImageView(context);
    }

    void controlMediaPlayer(Context context, File file, PlayToPauseMorphingImageView refs) {
        Log.d(TAG, "controlMediaPlayer: id:" + refs.getId());

        if (player.isPlaying()) {
            Log.d(TAG, "controlMediaPlayer: Player Paused");
            player.pause();
        } else if (!player.isPlaying() && this.currentId != -1) {
            Log.d(TAG, "controlMediaPlayer: Playing from local File");
            player.start();
            return;
        }
        if (this.currentId != refs.getId() && player.isPlaying()) {
            player.stop();
            Log.d(TAG, "controlMediaPlayer: Player stopped due to different Ids");
        }
        if (this.currentId != refs.getId()) {
            this.currentRefs.pauseMorph();
            this.currentId = refs.getId();
            this.currentRefs = refs;
            Log.d(TAG, "controlMediaPlayer: Setting up new Audio");
            try {
                Log.d(TAG, "controlMediaPlayer: Does File Exist? " + file.exists());
                player.reset();
                player.setDataSource(context, Uri.fromFile(file));
                player.prepareAsync();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    void controlMediaPlayer(Uri link, PlayToPauseMorphingImageView refs) {
        if (!NetworkStats.isNetworkAvailable(context))
            Toast.makeText(context, "Unable to Connect to Server", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "controlMediaPlayer: Playing from url: " + link.toString());
        // Starting stopping Audio
        if (player.isPlaying()) {
            Log.d(TAG, "controlMediaPlayer: Player Paused");
            player.pause();
        } else if (!player.isPlaying() && this.currentId != -1) {
            Log.d(TAG, "controlMediaPlayer: Playing from link");
            player.start();
            return;
        }
        if (this.currentId != refs.getId() && player.isPlaying()) {
            player.stop();
            Log.d(TAG, "controlMediaPlayer: Player stopped due to different Ids");
        }
        if (this.currentId != refs.getId()) {
            this.currentRefs.pauseMorph();
            this.currentRefs = refs;
            this.currentId = refs.getId();
            Log.d(TAG, "controlMediaPlayer: Setting up new Audio");
            try {
                player.reset();
                player.setDataSource(String.valueOf(link));
                player.prepareAsync();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
}
