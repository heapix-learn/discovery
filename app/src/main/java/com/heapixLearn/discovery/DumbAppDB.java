package com.heapixLearn.discovery;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.heapixLearn.discovery.db.posts.map_item.MapItem;
import com.heapixLearn.discovery.db.posts.map_item.MapItemDAO;
import com.heapixLearn.discovery.db.posts.post.Post;
import com.heapixLearn.discovery.db.posts.post.PostDAO;
import com.heapixLearn.discovery.db.posts.post.connections.image_connection.PostImageConnection;
import com.heapixLearn.discovery.db.posts.post.connections.image_connection.PostImageConnectionDAO;
import com.heapixLearn.discovery.db.posts.post.connections.video_connection.PostVideoConnection;
import com.heapixLearn.discovery.db.posts.post.connections.video_connection.PostVideoConnectionDAO;
import com.heapixLearn.discovery.db.posts.post.post_images.ImageDAO;
import com.heapixLearn.discovery.db.posts.post.post_images.PostImage;
import com.heapixLearn.discovery.db.posts.post.post_video.PostVideo;
import com.heapixLearn.discovery.db.posts.post.post_video.VideoDAO;


@Database(entities = {Post.class, MapItem.class, PostImage.class, PostVideo.class, PostImageConnection.class, PostVideoConnection.class}, version = 1)
public abstract class DumbAppDB extends RoomDatabase {
    public abstract PostDAO getPostDao();
    public abstract MapItemDAO getMapItemDao();
    public abstract ImageDAO getImageDao();
    public abstract VideoDAO getVideoDao();
    public abstract PostImageConnectionDAO getImageConnectionDao();
    public  abstract PostVideoConnectionDAO getVideoConnectionDao();

}
