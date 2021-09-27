package com.rosarycollege.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.firebase.storage.StorageReference;
import com.rosarycollege.teachbyvoice.R;

import java.io.File;

public class DownloadToCancelMorphingImageView extends androidx.appcompat.widget.AppCompatImageView {
    private AnimatedVectorDrawable downloadToCancel;
    private AnimatedVectorDrawable cancelToDownload;
    private AnimatedVectorDrawable cancelToComplete;
    private boolean mIsShowingCancelButton,exists;
    private Context context;
    private StorageReference reference;

    public DownloadToCancelMorphingImageView(Context context) {
        super(context);
        init(context);
    }

    public DownloadToCancelMorphingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DownloadToCancelMorphingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        mIsShowingCancelButton = false;
        downloadToCancel =
                (AnimatedVectorDrawable) AppCompatResources.getDrawable(context, R.drawable.from_download_to_cancel);
        cancelToDownload =
                (AnimatedVectorDrawable) AppCompatResources.getDrawable(context, R.drawable.from_cancel_to_download);
        cancelToComplete = (AnimatedVectorDrawable) AppCompatResources.getDrawable(context,R.drawable.from_cancel_to_check);
        setImageDrawable(downloadToCancel);
    }

    public File setReference(StorageReference reference) {
        File file = new File(context.getDir("media", Context.MODE_PRIVATE), reference.getName());
        if (file.exists()) {
            exists = true;
            setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.tick));
        }
        else {
            this.reference = reference;
        }
        return file;
    }

    public void morph() {
        if(!exists) {
            final AnimatedVectorDrawable drawable = mIsShowingCancelButton ? cancelToDownload : downloadToCancel;
            setImageDrawable(drawable);
            drawable.start();
            mIsShowingCancelButton = !mIsShowingCancelButton;
            new DownloadAssets(context).download(reference, this);
        }
    }

    public void morphToComplete(){
        final AnimatedVectorDrawable drawable = cancelToComplete;
        setImageDrawable(drawable);
        drawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        drawable.start();
    }
}