package com.heapixLearn.discovery.ui.map;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PostViewHolder> {
    static class PostViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout parentView;

        PostViewHolder(View parentView) {
            super(parentView);

            this.parentView = (ConstraintLayout) parentView;
        }

        void bind(View postPreview){
            parentView.removeView(postPreview);
            parentView.addView(postPreview);
        }
    }

    private List<View> postViews;

    public ListAdapter(List<View> postViews){
        this.postViews = postViews;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = new ConstraintLayout(viewGroup.getContext());
        return new PostViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return postViews.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        postViewHolder.bind(postViews.get(i));
    }
}
