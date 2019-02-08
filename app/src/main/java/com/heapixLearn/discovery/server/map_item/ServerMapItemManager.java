package com.heapixLearn.discovery.server.map_item;

import com.heapixLearn.discovery.RunnableWithObject;
import com.heapixLearn.discovery.db.AuthStore;
import com.heapixLearn.discovery.server.ServerAnswer;
import com.heapixLearn.discovery.server.TypeOfServerError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerMapItemManager {
    private MapItemApi mapItemApi;
    private AuthStore authStore;

    public ServerMapItemManager(){
        authStore = new AuthStore();
        mapItemApi = MapItemController.getApi(authStore.getToken());
    }

    public void getAllMapItems(final RunnableWithObject<List<ServerMapItem>> onSuccess,
                               final RunnableWithObject<TypeOfServerError> onFailure){
        mapItemApi.getAll().enqueue(new Callback<List<ServerMapItem>>() {
            @Override
            public void onResponse(Call<List<ServerMapItem>> call, Response<List<ServerMapItem>> response) {
                check_error(response.code(), onFailure);
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<List<ServerMapItem>> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void createMapItem(ServerMapItem mapItem,
                              final RunnableWithObject<ServerMapItem> onSuccess,
                              final RunnableWithObject<TypeOfServerError> onFailure){
        mapItemApi.createMapItem(mapItem).enqueue(new Callback<ServerMapItem>() {
            @Override
            public void onResponse(Call<ServerMapItem> call, Response<ServerMapItem> response) {
                check_error(response.code(), onFailure);

                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerMapItem> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void updateMapItem(ServerMapItem serverMapItem, Runnable onSuccess,
                              RunnableWithObject<TypeOfServerError> onFailure){
        getServerAnswerFromServer(mapItemApi.updateMapItem(serverMapItem.getPostId(), serverMapItem),
                onSuccess, onFailure);
    }

    public void deleteMapItem(ServerMapItem serverMapItem, Runnable onSuccess,
                              RunnableWithObject<TypeOfServerError> onFailure){
        getServerAnswerFromServer(mapItemApi.deleteMapItem(serverMapItem.getPostId()), onSuccess, onFailure);
    }

    public void getMapItemById(String postId, final RunnableWithObject<ServerMapItem> onSuccess,
                               final RunnableWithObject<TypeOfServerError> onFailure){
        mapItemApi.getById(postId).enqueue(new Callback<ServerMapItem>() {
            @Override
            public void onResponse(Call<ServerMapItem> call, Response<ServerMapItem> response) {

                check_error(response.code(), onFailure);

                if (response.body()!=null) {
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerMapItem> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    private void getServerAnswerFromServer(Call<ServerAnswer> call, final Runnable onSuccess,
                                           final RunnableWithObject<TypeOfServerError> onFailure) {
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
