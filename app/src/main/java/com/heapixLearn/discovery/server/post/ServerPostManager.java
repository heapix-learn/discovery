package com.heapixLearn.discovery.server.post;

import com.heapixLearn.discovery.RunnableWithObject;
import com.heapixLearn.discovery.db.AuthStore;
import com.heapixLearn.discovery.server.PostApi;
import com.heapixLearn.discovery.server.ServerAnswer;
import com.heapixLearn.discovery.server.TypeOfServerError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerPostManager {
    private PostApi postApi;
    private AuthStore authStore;


    public ServerPostManager() {
        authStore = new AuthStore();
        postApi = com.heapixLearn.discovery.server.PostController.getApi(authStore.getToken());
    }

    public void getPreviousPost(String id, final RunnableWithObject<ServerPost> onSuccess,
                            final RunnableWithObject<TypeOfServerError> onFailure){
        postApi.getPreviousPost(id).enqueue(new Callback<ServerPost>() {
            @Override
            public void onResponse(Call<ServerPost> call, Response<ServerPost> response) {
                check_error(response.code(), onFailure);
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerPost> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void getNextPost(String id, final RunnableWithObject<ServerPost> onSuccess,
                          final RunnableWithObject<TypeOfServerError> onFailure){
        postApi.getNextPost(id).enqueue(new Callback<ServerPost>() {
            @Override
            public void onResponse(Call<ServerPost> call, Response<ServerPost> response) {
                check_error(response.code(), onFailure);
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerPost> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void getPostsByUserId(String id, final RunnableWithObject<List<ServerPost>> onSuccess,
                               final RunnableWithObject<TypeOfServerError> onFailure){
        postApi.getPostsByUserId(id).enqueue(new Callback<List<ServerPost>>() {
            @Override
            public void onResponse(Call<List<ServerPost>> call, Response<List<ServerPost>> response) {
                check_error(response.code(), onFailure);
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<List<ServerPost>> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void getPartOfPost(String range, String id, final RunnableWithObject<List<ServerPost>> onSuccess,
                               final RunnableWithObject<TypeOfServerError> onFailure){
        postApi.getPartOfPosts(range, id).enqueue(new Callback<List<ServerPost>>() {
            @Override
            public void onResponse(Call<List<ServerPost>> call, Response<List<ServerPost>> response) {
                check_error(response.code(), onFailure);
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<List<ServerPost>> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void createPost(ServerPost serverPost, final RunnableWithObject<ServerPost> onSuccess,
                           final RunnableWithObject<TypeOfServerError> onFailure){
        postApi.createPost(serverPost).enqueue(new Callback<ServerPost>() {
            @Override
            public void onResponse(Call<ServerPost> call, Response<ServerPost> response) {
                check_error(response.code(), onFailure);

                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerPost> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void updatePost(ServerPost serverPost, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure){
        getServerAnswerFromServer(postApi.updatePost(serverPost.getId(), serverPost), onSuccess, onFailure);
    }

    private void getServerAnswerFromServer(Call<ServerAnswer> call, final Runnable onSuccess, final RunnableWithObject<TypeOfServerError> onFailure) {
        call.enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                check_error(response.code(), onFailure);

                if (response.body()!=null) {
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void deletePost(ServerPost serverPost, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure){
        getServerAnswerFromServer(postApi.deletePost(serverPost.getId()), onSuccess, onFailure);
    }

    public void getPostById(String id, final RunnableWithObject<ServerPost> onSuccess,
                               final RunnableWithObject<TypeOfServerError> onFailure){
        postApi.getPostById(id).enqueue(new Callback<ServerPost>() {
            @Override
            public void onResponse(Call<ServerPost> call, Response<ServerPost> response) {

                check_error(response.code(), onFailure);

                if (response.body()!=null) {
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerPost> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }


    private void check_error(int code, RunnableWithObject<TypeOfServerError> onFailure){
        switch (code){
            case 404:
                onFailure.init(TypeOfServerError.INFO_IS_ABSENT);
                onFailure.run();
                return;
            case 401:
                onFailure.init(TypeOfServerError.WRONG_CREDENTIALS);
                onFailure.run();
                return;
        }
        if (code/100==5){
            onFailure.init(TypeOfServerError.SERVER_ERROR);
            onFailure.run();
        }
    }

}
