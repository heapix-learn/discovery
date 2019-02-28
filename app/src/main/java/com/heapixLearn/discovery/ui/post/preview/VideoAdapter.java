package com.heapixLearn.discovery.ui.post.preview;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.heapixLearn.discovery.R;

import com.heapixLearn.discovery.ui.post.preview.entity.VideoItem;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private List<VideoItem> videoItems;

    VideoAdapter(Context context, List<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @Override
    public int getCount() {
        return videoItems.size();
    }

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
            view = inflater.inflate(R.layout.video_gallery_item, parent, false);
        } else {
            view = convertView;
        }

        ImageView thumbnail = (ImageView) view.findViewById(R.id.video_screen);
        thumbnail.setScaleType(RoundRectCornerImageView.ScaleType.CENTER_CROP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            thumbnail.setClipToOutline(true);
        }

        TextView duration = (TextView) view.findViewById(R.id.textTime);
        duration.setText(videoItems.get(position).getDuration());

        Glide.with(context)
                .load(videoItems.get(position).getThumbnailURL())
                .apply(new RequestOptions().override(300, 300))
                .apply(new RequestOptions().centerCrop())
                .into(thumbnail);

        view.setPadding(0, 0, 20, 0);
        return view;
    }
}
