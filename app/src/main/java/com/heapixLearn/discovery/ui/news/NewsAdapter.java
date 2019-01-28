package com.heapixLearn.discovery.ui.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.entity.VideoItem;
import com.heapixLearn.discovery.ui.full_screen_media_display.FullScreenPhoto;
import com.heapixLearn.discovery.ui.full_screen_media_display.FullScreenVideo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.content.ContextCompat.startActivity;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsItem> newsItems;
    private Context context;
    private static final String PHOTOS_URL="photosURL";
    private static final String POSITION="position";
    private static final String VIDEO_URL="videosURL";


    NewsAdapter(List<NewsItem> newsItems, Context context){
        this.newsItems = newsItems;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.ViewHolder viewHolder, int i) {
        NewsItem item = newsItems.get(i);
        viewHolder.postDescription.setText(item.getDescription());
        viewHolder.ownerOfPostAvatar.setImageURI(Uri.parse(item.getAvatar()));
        Glide.with(context)
                .asBitmap()
                .load(item.getAvatar())
                .into(viewHolder.ownerOfPostAvatar);
        viewHolder.nameOfPostOwner.setText(item.getNameOfOwner());
        viewHolder.ageOfPost.setText(item.getDate().toString());
        initPhotoGallery(item, viewHolder);
        initVideoGallery(item, viewHolder);
    }

    private void initPhotoGallery(final NewsItem item, final NewsAdapter.ViewHolder viewHolder){
        if (item.getPhotos().size()>0){
            viewHolder.photoTableRow.setVisibility(View.VISIBLE);
            PhotoAdapter photoAdapter = new PhotoAdapter(context, item.getPhotos());
            viewHolder.photoGallery.setDividerPadding(5);

            for (int j = 0; j< photoAdapter.getCount(); j++){
                View add = photoAdapter.getView(j, null, null);
                add.setId(j);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(context, FullScreenPhoto.class);
                        myIntent.putExtra(PHOTOS_URL, (String[]) item.getPhotos().toArray(new String[item.getPhotos().size()]));
                        myIntent.putExtra(POSITION, v.getId());
                        startActivity(context, myIntent, null);
                    }
                });
                viewHolder.photoGallery.addView(add);
            }
        }
    }

    private void initVideoGallery(final NewsItem item, final NewsAdapter.ViewHolder viewHolder){
        if (item.getVideoItems().size()>0){
            viewHolder.videoTableRow.setVisibility(View.VISIBLE);

            VideoAdapter videoAdapter = new VideoAdapter(context, item.getVideoItems());
            viewHolder.videoGallery.setDividerPadding(5);

            for (int j = 0; j< videoAdapter.getCount(); j++){
                View add = videoAdapter.getView(j, null, null);
                add.setId(j);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(context, FullScreenVideo.class);
                        myIntent.putExtra(VIDEO_URL, getStringArrayOfVideoURL(item.getVideoItems()));
                        myIntent.putExtra(POSITION, v.getId());
                        startActivity(context, myIntent, null);
                    }
                });
                viewHolder.videoGallery.addView(add);
            }
        }
    }

    private String[] getStringArrayOfVideoURL(List<VideoItem> videoItems){
        List<String> videosURL = new ArrayList<>();

        for (int k=0; k<videoItems.size(); k++){
            videosURL.add(videoItems.get(k).getVideoURL());
        }
        return (String[]) videosURL.toArray(new String[videosURL.size()]);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView ownerOfPostAvatar;
        TextView nameOfPostOwner;
        TextView ageOfPost;
        TextView postDescription;
        Boolean descriptionViewFlag = false;
        LinearLayout photoGallery;
        TableRow photoTableRow;
        LinearLayout videoGallery;
        TableRow videoTableRow;


        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerOfPostAvatar = itemView.findViewById(R.id.owner_photo);
            nameOfPostOwner = itemView.findViewById(R.id.owner_name);
            ageOfPost = itemView.findViewById(R.id.age_of_post);
            postDescription = itemView.findViewById(R.id.description_of_post);
            photoGallery = itemView.findViewById(R.id.photo_gallery_);
            photoTableRow = itemView.findViewById(R.id.photos_table_row);
            videoGallery = itemView.findViewById(R.id.video_gallery);
            videoTableRow = itemView.findViewById(R.id.videos_table_row);

            postDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!descriptionViewFlag) {
                        postDescription.setMaxLines(Integer.MAX_VALUE);
                        descriptionViewFlag = true;
                    } else {
                        postDescription.setMaxLines(3);
                        descriptionViewFlag = false;
                    }
                }
            });
        }
    }
}
