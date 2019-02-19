package com.heapixLearn.discovery.ui.map;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PostListLoader extends AsyncTask<List<Integer>, Void, List<View>> {
    private Runnable result;
    private  PostPreview preview;

    public PostListLoader(Context context) {
        preview = new PostPreview(context);
    }

    public void setResult(Runnable result) {
        this.result = result;
    }

    @Override
    protected void onPostExecute(List<View> views) {
        super.onPostExecute(views);
        result.run();
    }

    @Override
    protected List<View> doInBackground(List<Integer>... lists) {
        List<View> postPreviews = new ArrayList<>();
        for (int id : lists[0]) {
            postPreviews.add(preview.getPreview(id));
        }
        return postPreviews;
    }
}
