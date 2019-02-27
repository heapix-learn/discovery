package com.heapixLearn.discovery.ui.post.preview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.ui.post.preview.dummy.PostManager;
import com.heapixLearn.discovery.ui.post.preview.dummy.UserManager;
import com.heapixLearn.discovery.ui.post.preview.entity.IPost;
import com.heapixLearn.discovery.ui.post.preview.entity.IUser;
import com.heapixLearn.discovery.ui.post.preview.entity.VideoItem;
import com.heapixLearn.discovery.ui.post.preview.fullscreen_media.FullscreenPhotoActivity;
import com.heapixLearn.discovery.ui.post.preview.fullscreen_media.FullscreenVideoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostPreviewFragment extends Fragment implements View.OnClickListener {
    View previewFragment;

    private static final String PHOTOS_URL = "photosURL";
    private static final String POSITION ="position";
    private static final String VIDEO_URL = "videosURL";


    CircleImageView avatar;
    TextView username;
    TextView nameLocation;
    TextView followers;
    Button btnFullscreen;
    Button btnFollow;
    Button btnLike;
    Button btnComment;
    TextView btnOpenInMaps;
    TextView quantityLikes;
    TextView quantityComments;

    TableRow photoTableRow;
    TableRow videoTableRow;
    LinearLayout photoGallery;
    LinearLayout videoGallery;


    IPost currentPost;
    IUser currentUser;
    IPostManager postManager;
    IUserManager userManager;

    final Runnable onFollowSuccess = new Runnable() {
        @Override
        public void run() {
            if(currentUser.isFriend()){
                currentUser.setFriend(false);
                btnFollow.setBackgroundResource(R.drawable.button_follow);
                Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                        .getString(R.string.contact_unfollow), Toast.LENGTH_SHORT).show();
            } else {
                currentUser.setFriend(true);
                btnFollow.setBackgroundResource(R.drawable.button_follower);
                Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                        .getString(R.string.contact_follow), Toast.LENGTH_SHORT).show();
            }

        }
    };

    final Runnable onFollowFailure = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                            .getString(R.string.follow_on_failure), Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onLikeSuccess = new Runnable() {
        @Override
        public void run() {
            if(currentUser.isPostLiked(currentPost.getId())){
                currentUser.setLiked(currentPost.getId(), false);
                btnLike.setBackgroundResource(R.drawable.button_like);
                Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                        .getString(R.string.post_not_liked), Toast.LENGTH_SHORT).show();
            } else {
                currentUser.setLiked(currentPost.getId(), true);
                btnLike.setBackgroundResource(R.drawable.button_liked);
                Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                        .getString(R.string.post_liked), Toast.LENGTH_SHORT).show();
            }

        }
    };

    final Runnable onLikeFailure = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                    .getString(R.string.like_on_failure), Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onFullscreenSuccess = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), "Open fullscreen", Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onFullscreenFailure = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), "Unable to open fullscreen", Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onCommentsSuccess = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), "Open comments", Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onCommentsFailure = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), "Unable to open comments", Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onMapSuccess = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), "Open map", Toast.LENGTH_SHORT).show();
        }
    };

    final Runnable onMapFailure = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getContext(), "Unable to open map", Toast.LENGTH_SHORT).show();
        }
    };

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        previewFragment = inflater.inflate(R.layout.post_preview_item, container, false);

        postManager = new PostManager();
        userManager = new UserManager();
        //TODO : add id from other fragment via Bundle
        currentPost = postManager.getPostById(0);
        currentUser = userManager.getUserByAccountId(currentPost.getAccountId());

        avatar = previewFragment.findViewById(R.id.preview_avatar);
        username = previewFragment.findViewById(R.id.preview_name);
        nameLocation = previewFragment.findViewById(R.id.preview_location);
        followers = previewFragment.findViewById(R.id.preview_followers);
        btnFullscreen = previewFragment.findViewById(R.id.btn_fullscreen);
        btnFollow = previewFragment.findViewById(R.id.btn_follow);
        btnLike = previewFragment.findViewById(R.id.btn_like);
        btnComment = previewFragment.findViewById(R.id.btn_comment);
        btnOpenInMaps = previewFragment.findViewById(R.id.tv_open_in_maps);
        quantityLikes = previewFragment.findViewById(R.id.quantity_likes);
        quantityComments = previewFragment.findViewById(R.id.quantity_comments);

        photoGallery = previewFragment.findViewById(R.id.photo_gallery);
        photoTableRow = previewFragment.findViewById(R.id.photos_preview_table_row);
        videoGallery = previewFragment.findViewById(R.id.video_gallery);
        videoTableRow = previewFragment.findViewById(R.id.videos_preview_table_row);

        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(currentUser.getAvatar())
                .into(avatar);

        username.setText(currentUser.getName());
        followers.setText(String.valueOf(currentUser.getFollowers()) + " " +
                getString(R.string.str_followers));

        nameLocation.setText(currentPost.getNameLocation());
        quantityLikes.setText(currentPost.getLikes() + " " + getString(R.string.str_likes));
        quantityComments.setText(currentPost.getComments() + " " + getString(R.string.str_comments));

        if(currentUser.isFriend()){
            btnFollow.setBackgroundResource(R.drawable.button_follower);
        }

        if(currentUser.isPostLiked(currentPost.getId())){
            btnLike.setBackgroundResource(R.drawable.button_liked);
        }

        btnFullscreen.setOnClickListener(this);
        btnFollow.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        btnOpenInMaps.setOnClickListener(this);

        initPhotos();
        initVideos();

        return previewFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fullscreen:
                postManager.openFullScreen(onFullscreenSuccess, onFullscreenFailure);
                break;
            case R.id.btn_follow:
                if(postManager != null){
                    postManager.update(currentPost, onFollowSuccess, onFollowFailure);
                } else {
                    Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                                    .getString(R.string.unable_to_update_data), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_like:
                if(postManager != null){
                    postManager.update(currentPost, onLikeSuccess, onLikeFailure);
                } else {
                    Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                            .getString(R.string.unable_to_update_data), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_comment:
                if(postManager != null){
                    postManager.openComments(onCommentsSuccess, onCommentsFailure);
                } else {
                    Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                            .getString(R.string.unable_to_update_data), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_open_in_maps:
                if(postManager != null){
                    postManager.openInMap(onMapSuccess, onMapFailure);
                } else {
                    Toast.makeText(getContext(), Objects.requireNonNull(getContext())
                            .getString(R.string.unable_to_update_data), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initPhotos(){
        if (currentPost.getPhotos().size()>0){
            photoTableRow.setVisibility(View.VISIBLE);
            PhotoAdapter photoAdapter = new PhotoAdapter(previewFragment.getContext(),
                    currentPost.getPhotos());
            photoGallery.setDividerPadding(5);

            for (int j = 0; j< photoAdapter.getCount(); j++){
                View add = photoAdapter.getView(j, null, null);
                add.setId(j);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(previewFragment.getContext(),
                                FullscreenPhotoActivity.class);
                        myIntent.putExtra(PHOTOS_URL, (String[]) currentPost.getPhotos()
                                .toArray(new String[currentPost.getPhotos().size()]));
                        myIntent.putExtra(POSITION, v.getId());
                        startActivity(myIntent, null);
                    }
                });
                photoGallery.addView(add);
            }
        }
    }
    private void initVideos(){
        if (currentPost.getVideos().size()>0){
            videoTableRow.setVisibility(View.VISIBLE);

            VideoAdapter videoAdapter = new VideoAdapter(previewFragment.getContext(),
                    currentPost.getVideos());
            videoGallery.setDividerPadding(5);

            for (int j = 0; j< videoAdapter.getCount(); j++){
                View add = videoAdapter.getView(j, null, null);
                add.setId(j);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(previewFragment.getContext(),
                                FullscreenVideoActivity.class);
                        myIntent.putExtra(VIDEO_URL, getStringArrayOfVideoURL(currentPost.getVideos()));
                        myIntent.putExtra(POSITION, v.getId());
                        //deleted context
                        startActivity(myIntent, null);
                    }
                });
                videoGallery.addView(add);
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
}
