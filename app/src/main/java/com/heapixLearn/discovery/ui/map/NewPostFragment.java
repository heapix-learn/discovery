package com.heapixLearn.discovery.ui.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.dummy.PostManager;
import com.heapixLearn.discovery.dummy.PostManagerInterface;
import com.heapixLearn.discovery.entity.map.Post;
import com.heapixLearn.discovery.entity.map.PostInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NewPostFragment extends Fragment {
    protected EditText textTitle;
    protected EditText textDescription;
    private AutoCompleteTextView textLocation;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    protected PostInterface post;
    private int PHOTO = 0;
    private int VIDEO = 1;

    private ImageButton lockButton;

    static final int GALLERY_REQUEST = 1;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;

    private MediaAdapterGrid photoAdapterGrid;
    private MediaAdapterGrid videoAdapterGrid;
    private LinearLayout deleteNavigation;
    private PostManagerInterface postManager = new PostManager();

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View postFragment = inflater.inflate(R.layout.new_post_fragment, container,false);
        post = new Post();

        textTitle = (EditText) postFragment.findViewById(R.id.textTitle_);
        textDescription = (EditText) postFragment.findViewById(R.id.textDescription_);
        textLocation = (AutoCompleteTextView) postFragment.findViewById(R.id.textLocation_);
        lockButton = (ImageButton) postFragment.findViewById(R.id.lock__);
        gridViewForPhoto = (GridView) postFragment.findViewById(R.id.gridViewForPhoto_);
        gridViewForVideo = (GridView) postFragment.findViewById(R.id.gridViewForVideo_);
        textNumberOfPhoto = (TextView) postFragment.findViewById(R.id.number_of_photo_);
        textNumberOfVideo = (TextView) postFragment.findViewById(R.id.number_of_video_);
        ImageButton addPostButton = (ImageButton) postFragment.findViewById(R.id.add_post__);
        ImageButton addMediaButton = (ImageButton) postFragment.findViewById(R.id.add_media__);
        Button deleteMedia = (Button) postFragment.findViewById(R.id.delete_media);
        Button cancelDeleteMedia = (Button) postFragment.findViewById(R.id.cancel_delete);
        ImageButton myLocation = (ImageButton) postFragment.findViewById(R.id.my_location);
        deleteNavigation = (LinearLayout) postFragment.findViewById(R.id.delete_navigation);
        cancelDeleteMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBoxesAreGone();
            }
        });
        deleteMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeleteMedia();
            }
        });
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLocationForPost();
            }
        });

        textDescription.setMovementMethod(new ScrollingMovementMethod());
        photoAdapterGrid = new MediaAdapterGrid(this, post.getPhotos(), MediaAdapterGrid.TYPE_PHOTO);
        videoAdapterGrid = new MediaAdapterGrid(this, post.getVideos(), MediaAdapterGrid.TYPE_VIDEO);


        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(getContext());
        textLocation.setAdapter(adapter);
        textLocation.setOnItemClickListener(onItemClickListener);

        AddTouchListenerForTextDescription();
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddPost();
            }
        });
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddMedia();
            }
        });
        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLock();
            }
        });

        AddGarbage();

        return postFragment;
    }




    private void MediaGalleryUpdate(MediaAdapterGrid mediaAdapterGrid, GridView gridView, List<Uri> mediaItems, TextView textNumber, int MEDIA_TYPE){
        if (mediaItems.size() != 0) {
            textNumber.setVisibility(View.VISIBLE);
            if (gridView.getWidth() != 0) {
                int t = 0;
                if (mediaItems.size() % 3 == 0) t = 1;
                gridView.setLayoutParams(new LinearLayout.LayoutParams(gridView.getWidth(), ((mediaItems.size() / 3) + 1 - t) * 310));
            }
            if (MEDIA_TYPE == VIDEO) {
                textNumber.setText(mediaItems.size() + " " + getString(R.string.videos));
            } else {
                textNumber.setText(mediaItems.size() + " " + getString(R.string.photos));
            }
            mediaAdapterGrid.notifyDataSetChanged();
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(mediaAdapterGrid);
        } else{
            gridView.setVisibility(View.GONE);
            textNumber.setVisibility(View.GONE);
            mediaAdapterGrid.notifyDataSetChanged();
        }
    }

    public void CheckBoxesAreVisible(){
        photoAdapterGrid.CheckBoxIsVisible();
        videoAdapterGrid.CheckBoxIsVisible();
        deleteNavigation.setVisibility(View.VISIBLE);
    }
    public void CheckBoxesAreGone(){
        photoAdapterGrid.CheckBoxIsGONE();
        videoAdapterGrid.CheckBoxIsGONE();
        deleteNavigation.setVisibility(View.GONE);
    }

    private void onClickDeleteMedia(){

        deleteMedia(photoAdapterGrid, post.getPhotos());
        deleteMedia(videoAdapterGrid, post.getVideos());
        CheckBoxesAreGone();
        MediaGalleryUpdate(photoAdapterGrid, gridViewForPhoto, post.getPhotos(), textNumberOfPhoto, PHOTO);
        MediaGalleryUpdate(videoAdapterGrid, gridViewForVideo, post.getVideos(), textNumberOfVideo, VIDEO);
    }

    void deleteMedia(MediaAdapterGrid adapterGrid, List<Uri> media){
        CheckBox[] mediaCheckBoxes = adapterGrid.getCheckBoxes();
        List<Uri> forDel= new ArrayList<>();
        for (int i=0; i<mediaCheckBoxes.length; i++){
            if (mediaCheckBoxes[i].isChecked()) {
                forDel.add(media.get(i));
            }
        }

        for (int i=0; i<forDel.size(); i++){
            media.remove(forDel.get(i));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void AddTouchListenerForTextDescription(){
        textDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.textDescription_) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void onClickLock() {
        if (post.getAccess() == 0) {
            post.setAccess(1);
            lockButton.setImageResource(R.drawable.lock_close);
        } else {
            post.setAccess(0);
            lockButton.setImageResource(R.drawable.lock_open);
        }
    }

    public void onClickAddMedia() {
        CheckBoxesAreGone();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/* image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    private void AddLocationForPost() {
        MyLocationListener.SetUpLocationListener(getContext());
        if (MyLocationListener.imHere == null) {
            Toast.makeText(getContext(), getString(R.string.geolocation_error), Toast.LENGTH_SHORT).show();
            return;
        }

        post.setLocation(MyLocationListener.imHere.getLatitude(), MyLocationListener.imHere.getLongitude());
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocation(MyLocationListener.imHere.getLatitude(), MyLocationListener.imHere.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setNameLocation(list.get(0).getLocality());
        textLocation.setText(post.getNameLocation());
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(textLocation.getText().toString(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.setNameLocation(textLocation.getText().toString());
            post.setLocation(list.get(0).getLatitude(), list.get(0).getLongitude());
        }
    };


    protected void AddGarbage() {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedItem = imageReturnedIntent.getData();
                    if (checkType(selectedItem.toString()) == 0) {
                        post.setPhoto(selectedItem);
                        MediaGalleryUpdate(photoAdapterGrid, gridViewForPhoto, post.getPhotos(), textNumberOfPhoto, PHOTO);
                    } else {
                        post.setVideo(selectedItem);
                        MediaGalleryUpdate(videoAdapterGrid, gridViewForVideo, post.getVideos(), textNumberOfVideo, VIDEO);
                    }
                }
        }
    }

    public int checkType(String str) {
        int index = str.lastIndexOf("/images/");
        if (index != -1) return 0;
        else return 1;
    }


    private void onClickAddPost() {

        post.setTitle(textTitle.getText().toString());
        post.setDescription(textDescription.getText().toString());

        if (post.getLocation().getLatitude() < -999 && post.getLocation().getLongitude() < -999) {
            AddLocationForPost();
        }

        postManager.addPost(post);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


}