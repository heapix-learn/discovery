package com.heapixLearn.discovery.ui.map;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.heapixLearn.discovery.R;

import java.util.List;

public class PostListDialog {
    private List<Integer> postIds;
    private Context context;
    private View dialogView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public PostListDialog(List<Integer> postIds, Context context) {
        this.postIds = postIds;
        this.context = context;

        initViewFields();
    }

    private void initViewFields() {
        dialogView = View.inflate(context, R.layout.dialog_post_list, null);
        recyclerView = dialogView.findViewById(R.id.rv);
        progressBar = dialogView.findViewById(R.id.progress_bar);
    }

    private void initList(List<View> postPreviews) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

        recyclerView.setHasFixedSize(true);

        ListAdapter adapter = new ListAdapter(postPreviews);
        recyclerView.setAdapter(adapter);
    }

    public void showPostList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.show();
        getPostPreviews();
    }

    private void getPostPreviews() {
        PostListLoader loader = new PostListLoader(context);
        loader.setResult(() -> {
            try {
                initList(loader.get());
                progressBar.setVisibility(View.INVISIBLE);
            } catch (Exception e){}
        });
        loader.execute(postIds);
    }

}
