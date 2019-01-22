package com.heapixLearn.discovery.Server.Contacts;


import com.heapixLearn.discovery.DB.AuthStore;
import com.heapixLearn.discovery.RunnableWithObject;
import com.heapixLearn.discovery.Server.Controller;
import com.heapixLearn.discovery.Server.ServerAnswer;
import com.heapixLearn.discovery.Server.TypeOfServerError;
import com.heapixLearn.discovery.Server.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerContactManager {

    private UserApi userApi;
    private AuthStore authStore;

    public ServerContactManager(){
        userApi = Controller.getApi();
        authStore = AuthStore.getInstance();
    }

    public void readContacts(final RunnableWithObject<List<ServerContact>> onSuccess, final RunnableWithObject<TypeOfServerError> onFailure){
        userApi.readContacts(authStore.getToken()).enqueue(new Callback<List<ServerContact>>() {
            @Override
            public void onResponse(Call<List<ServerContact>> call, Response<List<ServerContact>> response) {
                if (isExist(response.body())){
                    onSuccess.init(response.body());
                } else {
                    onFailure.init(TypeOfServerError.SERVER_ERROR);
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<List<ServerContact>> call, Throwable t) {
                onFailure.init(TypeOfServerError.SERVER_ERROR);
                onFailure.run();
            }
        });
    }

    public void createContact(ServerContact serverContact, final Runnable onSuccess, final RunnableWithObject<TypeOfServerError> onFailure){
        goWithCallback(userApi.createContact(serverContact, authStore.getToken()), onSuccess, onFailure);
    }

    public void updateContact(ServerContact serverContact, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure){
        goWithCallback(userApi.updateContact(serverContact, authStore.getToken()), onSuccess, onFailure);
    }

    public void deleteContact(ServerContact serverContact, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure){
        goWithCallback(userApi.deleteContact(serverContact, authStore.getToken()), onSuccess, onFailure);
    }

    private boolean isExist(List<ServerContact> body){
        return body!=null;
    }

    private void goWithCallback(Call<ServerAnswer> call, final Runnable onSuccess, final RunnableWithObject<TypeOfServerError> onFailure){
        call.enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (isExist(response.body())) {
                    if (isSuccess(response.body())) {
                        onSuccess.run();
                    } else {
                        onFailure.init(TypeOfServerError.SERVER_ERROR);
                        onFailure.run();
                    }
                } else {
                    onFailure.init(TypeOfServerError.SERVER_ERROR);
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.init(TypeOfServerError.SERVER_ERROR);
                onFailure.run();
            }
        });
    }

    private boolean isExist(ServerAnswer serverAnswer){
        return serverAnswer!=null;
    }

    private boolean isSuccess(ServerAnswer serverAnswer){
        return serverAnswer.getSuccess();
    }

}
