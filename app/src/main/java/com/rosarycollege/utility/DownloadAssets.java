package com.rosarycollege.utility;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DownloadAssets {
    private static final String TAG = "DownloadAssets";
    private final File mediaDirectory;

    public DownloadAssets(Context context) {
        mediaDirectory = context.getDir("media", Context.MODE_PRIVATE);
    }

    void download(StorageReference reference, DownloadToCancelMorphingImageView downloadToCancelMorphingImageView, ProgressBar bar) {
        try {
            File fileWithinMyDir = new File(mediaDirectory, reference.getName());
            fileWithinMyDir.createNewFile();
            bar.setVisibility(View.VISIBLE);
            reference.getFile(fileWithinMyDir)
                    .addOnSuccessListener(taskSnapshot -> {
                        // make the download button green after complete
                        downloadToCancelMorphingImageView.morphToComplete();
                        bar.setVisibility(View.INVISIBLE);
                    }).addOnProgressListener(snapshot ->
                        bar.setProgress((int) ((100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount()),true)
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}