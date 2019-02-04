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



import com.heapixLearn.discovery.R;

import java.util.List;

public class NewsFragment extends Fragment {

    private View newsFragment;

    private NewsManager newsManager;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsItem> newsItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsFragment = inflater.inflate(R.layout.news_fragment, container, false);

        initPosts();
        return newsFragment;
    }

    private void initPosts(){
        //TODO initialization newsManager
        recyclerView = (RecyclerView) newsFragment.findViewById(R.id.news_recycle_view);
        newsItems = newsManager.getAll();
        adapter = new NewsAdapter(newsItems, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void updateAll(List<NewsItem> news){
        newsItems.clear();
        newsItems.addAll(news);
        adapter.notifyDataSetChanged();
    }

    public void updateNewsItem(NewsItem newNewsItem){
        newsItems.remove(newNewsItem);
        newsItems.add(newNewsItem);
        adapter.notifyDataSetChanged();
    }
}
