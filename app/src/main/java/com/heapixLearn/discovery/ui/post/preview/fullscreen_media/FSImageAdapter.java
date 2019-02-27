package com.heapixLearn.discovery.ui.post.preview.fullscreen_media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.heapixLearn.discovery.R;

import java.util.List;

public class FSImageAdapter extends PagerAdapter {
    private Context context;
    private List<String> GalImages;
    private LayoutInflater mLayoutInflater;

    FSImageAdapter(Context context, List<String> GalImages){
        this.context=context;
        this.GalImages=GalImages;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return GalImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item_photo, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.photoView);
        Glide.with(context).load(GalImages.get(position)).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}