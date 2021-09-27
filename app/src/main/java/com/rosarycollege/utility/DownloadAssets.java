package com.rosarycollege.utility;

import android.content.Context;
import android.util.Log;

import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DownloadAssets {
    private static final String TAG = "DownloadAssets";
    private final File mediaDirectory;

    public DownloadAssets(Context context) {
        mediaDirectory = context.getDir("media", Context.MODE_PRIVATE);
    }

    void download(StorageReference reference, DownloadToCancelMorphingImageView downloadToCancelMorphingImageView) {
        try {
            File fileWithinMyDir = new File(mediaDirectory, reference.getName());
            fileWithinMyDir.createNewFile();
            reference.getFile(fileWithinMyDir)
                    .addOnSuccessListener(taskSnapshot -> {
                        // make the download button green after complete
                        downloadToCancelMorphingImageView.morphToComplete();
                    }).addOnProgressListener(snapshot -> {
                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        Log.d(TAG, "download: "+progress);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}