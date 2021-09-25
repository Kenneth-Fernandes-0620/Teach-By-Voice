package com.rosarycollege.utility;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.storage.StorageReference;

public class DownloadAssets extends androidx.appcompat.widget.AppCompatImageView{
    private StorageReference reference;
    public DownloadAssets(@NonNull Context context) {
        super(context);
    }

    public DownloadAssets(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DownloadAssets(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
