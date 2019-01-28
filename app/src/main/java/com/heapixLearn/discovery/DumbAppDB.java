package com.heapixLearn.discovery;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.heapixLearn.discovery.db.posts.map_item.MapItem;
import com.heapixLearn.discovery.db.posts.map_item.MapItemDAO;
import com.heapixLearn.discovery.db.posts.post.Post;
import com.heapixLearn.discovery.db.posts.post.PostDAO;
import com.heapixLearn.discovery.db.posts.post.post_images.ImageDAO;
import com.heapixLearn.discovery.db.posts.post.post_images.PostImage;
import com.heapixLearn.discovery.db.posts.post.post_video.PostVideo;
import com.heapixLearn.discovery.db.posts.post.post_video.VideoDAO;


@Database(entities = {Post.class, MapItem.class, PostImage.class, PostVideo.class}, version = 2)
public abstract class DumbAppDB extends RoomDatabase {
    public abstract PostDAO getPostDao();
    public abstract MapItemDAO getMapItemDao();
    public abstract ImageDAO getImageDao();
    public abstract VideoDAO getVideoDao();
}
