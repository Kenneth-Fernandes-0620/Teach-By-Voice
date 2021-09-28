package com.rosarycollege.utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;
import com.rosarycollege.teachbyvoice.R;

import java.io.File;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.Viewholder> {
    private List<StorageReference> itemList;
    private final MediaPlayer player;

    public recyclerViewAdapter(List<StorageReference> itemList, MediaPlayer player) {
        this.itemList = itemList;
        this.player = player;
    }

    public void refreshData(List<StorageReference> itemList, int changeStart, int changeEnd){
        this.itemList = itemList;
        if (player.isPlaying())
            player.stop();
        player.reset();
        player.release();
        this.notifyItemRangeInserted(changeStart,changeEnd);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if (itemList.size() > position) {
            holder.text.setText(itemList.get(position).getName());
            File f = (holder.downloadToCancelMorphingImageView.setReference(itemList.get(position)));
            if (!f.exists()) {
                holder.playToPauseImageView.setLink(itemList.get(position).getDownloadUrl(), 0, position, player);
            } else {
                holder.playToPauseImageView.setLink(f, 1, position, player);
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView text;
        PlayToPauseMorphingImageView playToPauseImageView;
        DownloadToCancelMorphingImageView downloadToCancelMorphingImageView;
        ProgressBar progressBar;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.Title);
            playToPauseImageView = itemView.findViewById(R.id.imageView);
            downloadToCancelMorphingImageView = itemView.findViewById(R.id.DownloadMorphingView);
            progressBar = itemView.findViewById(R.id.progressBar);
            playToPauseImageView.setOnClickListener(view -> playToPauseImageView.morph());
            downloadToCancelMorphingImageView.setOnClickListener(view -> downloadToCancelMorphingImageView.morph(progressBar));
        }
    }
}
