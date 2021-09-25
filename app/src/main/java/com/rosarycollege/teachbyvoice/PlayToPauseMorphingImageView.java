package com.rosarycollege.teachbyvoice;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;

public class PlayToPauseMorphingImageView extends androidx.appcompat.widget.AppCompatImageView {

    private AnimatedVectorDrawable mArrowToCheckMark;
    private AnimatedVectorDrawable mCheckMarkToArrow;
    private boolean mIsShowingCheckMark;

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

    public void init(Context context) {
        mIsShowingCheckMark = false;
        mArrowToCheckMark =
                (AnimatedVectorDrawable) AppCompatResources.getDrawable(context, R.drawable.from_play_to_pause);
        mCheckMarkToArrow =
                (AnimatedVectorDrawable) AppCompatResources.getDrawable(context, R.drawable.from_pause_to_play);
        setImageDrawable(mArrowToCheckMark);
    }

    public void morph() {
        final AnimatedVectorDrawable drawable = mIsShowingCheckMark ? mCheckMarkToArrow : mArrowToCheckMark;
        setImageDrawable(drawable);
        drawable.start();
        mIsShowingCheckMark = !mIsShowingCheckMark;
    }

}