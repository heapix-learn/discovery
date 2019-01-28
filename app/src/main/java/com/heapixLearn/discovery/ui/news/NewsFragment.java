package com.heapixLearn.discovery.ui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heapixLearn.discovery.NewsManager;
import com.heapixLearn.discovery.R;

public class NewsFragment extends Fragment {

    private View newsFragment;
    private NewsManager newsManager = new NewsManager();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsFragment = inflater.inflate(R.layout.news_fragment, container, false);
        initPosts();
        return newsFragment;
    }

    private void initPosts(){
        RecyclerView recyclerView = (RecyclerView) newsFragment.findViewById(R.id.news_recycle_view);
        NewsAdapter adapter = new NewsAdapter(newsManager.getAll(), getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }
}
