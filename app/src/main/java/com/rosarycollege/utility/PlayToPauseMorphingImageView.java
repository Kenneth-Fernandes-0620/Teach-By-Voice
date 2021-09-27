package com.rosarycollege.utility;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.tasks.Task;
import com.rosarycollege.teachbyvoice.R;

import java.io.File;

public class PlayToPauseMorphingImageView extends androidx.appcompat.widget.AppCompatImageView {

    private AnimatedVectorDrawable playToPause;
    private AnimatedVectorDrawable pauseToPlay;
    private boolean mIsShowingPauseButton;
    private com.rosarycollege.utility.MediaPlayer mediaPlayer;
    private Uri link;
    private File file;
    private int storageState;
    private int id;

    public PlayToPauseMorphingImageView(Context context) {
        super(context);
        init(context);
    }

    public PlayToPauseMorphingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayToPauseMorphingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public int getId() {
        return id;
    }

    public void init(Context context) {
        mIsShowingPauseButton = false;
        playToPause =
                (AnimatedVectorDrawable) AppCompatResources.getDrawable(context, R.drawable.from_play_to_pause);
        pauseToPlay =
                (AnimatedVectorDrawable) AppCompatResources.getDrawable(context, R.drawable.from_pause_to_play);
        setImageDrawable(playToPause);
    }


    public void setLink(Task<Uri> link, int State, int id, MediaPlayer player) {
        this.storageState = State;
        link.addOnSuccessListener(uri -> this.link = uri);
        this.id = id;
        Log.d("PlayToPause", "setReference: " + link);
        mediaPlayer = player;
    }

    public void setLink(File file, int State, int id, MediaPlayer player) {
        this.storageState = State;
        this.file = file;
        this.id = id;
        mediaPlayer = player;
    }

    public void morph() {
        final AnimatedVectorDrawable drawable = mIsShowingPauseButton ? pauseToPlay : playToPause;
        setImageDrawable(drawable);
        drawable.start();
        if (storageState == 0)
            mediaPlayer.controlMediaPlayer(this.link, this);
        else
            mediaPlayer.controlMediaPlayer(getContext(), this.file, this);
        mIsShowingPauseButton = !mIsShowingPauseButton;
    }

    public void pauseMorph() {
        final AnimatedVectorDrawable drawable = mIsShowingPauseButton ? pauseToPlay : playToPause;
        setImageDrawable(drawable);
        drawable.start();
        mIsShowingPauseButton = !mIsShowingPauseButton;
    }
}