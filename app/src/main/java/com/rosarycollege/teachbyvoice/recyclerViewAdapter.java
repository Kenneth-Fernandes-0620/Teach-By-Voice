package com.rosarycollege.teachbyvoice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.Viewholder>{
    private final ArrayList<String> itemName;

    public recyclerViewAdapter(ArrayList<String> itemName) {
        this.itemName = itemName;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if (itemName.size() > position)
            holder.text.setText(itemName.get(position));
    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        RelativeLayout recycleViewLayout;
        TextView text;
        PlayToPauseMorphingImageView mArrowToCheckMarkImageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.Title);
            mArrowToCheckMarkImageView = itemView.findViewById(R.id.imageView);
            mArrowToCheckMarkImageView.setOnClickListener(view -> mArrowToCheckMarkImageView.morph());
            recycleViewLayout = itemView.findViewById(R.id.parent_Item);
        }
    }
}
