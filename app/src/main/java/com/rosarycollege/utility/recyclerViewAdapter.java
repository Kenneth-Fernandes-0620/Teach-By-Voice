package com.rosarycollege.utility;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;
import com.rosarycollege.teachbyvoice.R;

import java.io.File;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.Viewholder> {
    private final List<StorageReference> itemList;
    private static final String TAG = "recycleViewAdapter";
    public recyclerViewAdapter(List<StorageReference> itemList) {
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Inside");
        if (itemList.size() > position) {
            holder.text.setText(itemList.get(position).getName());
            File f = (holder.downloadToCancelMorphingImageView.setReference(itemList.get(position)));
            if (!f.exists()) {
                holder.playToPauseImageView.setLink(itemList.get(position).getDownloadUrl(), 0);
                Log.d(TAG, "onBindViewHolder: File Does Not Exist");
            } else {
                holder.playToPauseImageView.setLink(f, 1);
                Log.d(TAG, "onBindViewHolder: File Exists");
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        RelativeLayout recycleViewLayout;
        TextView text;
        PlayToPauseMorphingImageView playToPauseImageView;
        DownloadToCancelMorphingImageView downloadToCancelMorphingImageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.Title);
            playToPauseImageView = itemView.findViewById(R.id.imageView);
            downloadToCancelMorphingImageView = itemView.findViewById(R.id.DownloadMorphingView);
            playToPauseImageView.setOnClickListener(view -> playToPauseImageView.morph());
            downloadToCancelMorphingImageView.setOnClickListener(view -> downloadToCancelMorphingImageView.morph());
            recycleViewLayout = itemView.findViewById(R.id.parent_Item);
        }
    }
}
