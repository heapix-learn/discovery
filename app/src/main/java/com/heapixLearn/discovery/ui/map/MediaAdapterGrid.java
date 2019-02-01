package com.heapixLearn.discovery.ui.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.heapixLearn.discovery.R;
import java.io.IOException;
import java.util.List;

public class MediaAdapterGrid extends BaseAdapter {
    static final int TYPE_PHOTO = 0;
    static final int TYPE_VIDEO = 1;
    private NewPostFragment parentFragment;
    private List<Uri> mediaItems;
    private int mediaType;
    private CheckBox[] checkBoxes;

    MediaAdapterGrid(NewPostFragment parentFragment, List<Uri> mediaItems, int mediaType) {
        this.parentFragment = parentFragment;
        this.mediaItems = mediaItems;
        this.mediaType = mediaType;
        this.checkBoxes = new CheckBox[mediaItems.size()];
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mediaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       if (mediaType == TYPE_VIDEO) {
           return isVideo(position, convertView, parent);
       }
       if (mediaType == TYPE_PHOTO) {
            return isPhoto(position, convertView, parent);
       }
        return null;
    }

    private View isPhoto(final int position, View convertView, ViewGroup parent){
        View grid = initView(convertView, parent);
        ImageView photo = (ImageView) grid.findViewById(R.id.img_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            photo.setClipToOutline(true);
        }

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(parentFragment.getContext().getContentResolver(), mediaItems.get(position));
        } catch (IOException e) {
        }

        photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photo.setImageBitmap(bitmap);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (checkBoxes[position]==null) {
            checkBoxes[position] =  (CheckBox) grid.findViewById(R.id.checkBox);
        }

        photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                parentFragment.CheckBoxesAreVisible();
                return false;
            }
        });
        return grid;
    }

    private View isVideo(final int position, View convertView, ViewGroup parent){
        View grid = initView(convertView, parent);
        TextView videoTime = grid.findViewById(R.id.textTime);
        videoTime.setVisibility(View.VISIBLE);

        ImageView thumbnail = (ImageView) grid.findViewById(R.id.img_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            thumbnail.setClipToOutline(true);
        }

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        MediaMetadataRetriever mMMR = new MediaMetadataRetriever();
        mMMR.setDataSource(parentFragment.getContext(), mediaItems.get(position));
        Bitmap bitmap = mMMR.getFrameAtTime(10);
        String time = mMMR.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        if (checkBoxes[position]==null) {
            checkBoxes[position] =  (CheckBox) grid.findViewById(R.id.checkBox);
        }

        videoTime.setText(convertSecondsToHMmSs(Long.parseLong(time)));
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setImageBitmap(bitmap);


        thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                parentFragment.CheckBoxesAreVisible();
                return false;
            }
        });
        return grid;
    }

    private View initView(View convertView, ViewGroup parent){
        View grid;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parentFragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.mygrid_layout, parent, false);
        } else grid = convertView;

        return grid;
    }

    void CheckBoxIsVisible(){
        for (CheckBox checkBoxe : checkBoxes) {
            checkBoxe.setVisibility(View.VISIBLE);
        }
    }

    CheckBox[] getCheckBoxes() {
        return checkBoxes;
    }

    void CheckBoxIsGONE(){
        for (int i=0; i<getCount(); i++){
            checkBoxes[i].setVisibility(View.GONE);
            checkBoxes[i].setChecked(false);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.checkBoxes = new CheckBox[this.mediaItems.size()];
    }

    private static String convertSecondsToHMmSs(long milliseconds) {
        long seconds = milliseconds / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        if (h == 0) {
            return String.format("%02d:%02d", m, s);
        }
        return String.format("%d:%02d:%02d", h, m, s);
    }
}
