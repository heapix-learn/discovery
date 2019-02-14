package com.heapixLearn.discovery.ui.post.preview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.heapixLearn.discovery.R;

import java.util.ArrayList;
import java.util.List;

public class PostPreviewAdapter extends RecyclerView.Adapter<PostPreviewAdapter.ViewHolder> {

    private List<String> imageList = new ArrayList<>();
    private Context context;

    public PostPreviewAdapter(List<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, viewGroup,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String currentPhoto = imageList.get(i);
        Glide.with(context)
                .asBitmap()
                .load(currentPhoto)
                .into(viewHolder.photoItem);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photoItem;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoItem = itemView.findViewById(R.id.preview_content_item);
        }
    }
}
