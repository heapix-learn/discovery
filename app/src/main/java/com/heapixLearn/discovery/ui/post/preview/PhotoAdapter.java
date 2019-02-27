package com.heapixLearn.discovery.ui.post.preview;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.heapixLearn.discovery.R;

import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String> photos;

    PhotoAdapter(Context context, List<String> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.photo_gallery_item, parent, false);
        } else {
            view = convertView;
        }

        ImageView photo = (ImageView) view.findViewById(R.id.img_photo_);
        photo.setScaleType(RoundRectCornerImageView.ScaleType.CENTER_CROP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            photo.setClipToOutline(true);
        }

        Glide.with(context).load(photos.get(position)).apply(new RequestOptions().override(600, 200))
                .apply(new RequestOptions().centerCrop()).into(photo);

        view.setPadding(0, 0, 20, 0);
        return view;
    }


}