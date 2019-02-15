package com.heapixLearn.discovery.server.contacts;


import com.heapixLearn.discovery.db.AuthStore;
import com.heapixLearn.discovery.RunnableWithObject;
import com.heapixLearn.discovery.server.Controller;
import com.heapixLearn.discovery.server.ServerAnswer;
import com.heapixLearn.discovery.server.TypeOfServerError;
import com.heapixLearn.discovery.server.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerContactManager {

    private UserApi userApi;
    private AuthStore authStore;

    public ServerContactManager(){
        authStore = new AuthStore();
        userApi = Controller.getApi(authStore.getToken());
    }

    public void readContacts(final RunnableWithObject<List<ServerContact>> onSuccess, final RunnableWithObject<TypeOfServerError> onFailure){
        userApi.readContacts().enqueue(new Callback<List<ServerContact>>() {
            @Override
            public void onResponse(Call<List<ServerContact>> call, Response<List<ServerContact>> response) {
                check_error(response.code(), onFailure);
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<List<ServerContact>> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void createContact(ServerContact serverContact, final RunnableWithObject<ServerContact> onSuccess, final RunnableWithObject<TypeOfServerError> onFailure){
        userApi.createContact(serverContact).enqueue(new Callback<ServerContact>() {
            @Override
            public void onResponse(Call<ServerContact> call, Response<ServerContact> response) {
                check_error(response.code(), onFailure);

                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerContact> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
    }

    public void updateContact(ServerContact serverContact, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure){
        getServerAnswerFromServer(userApi.updateContact(serverContact.getId(), serverContact), onSuccess, onFailure);
    }

    public void deleteContact(ServerContact serverContact, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure){
        getServerAnswerFromServer(userApi.deleteContact(serverContact.getId()), onSuccess, onFailure);
    }

    public void getContactById(String id, final RunnableWithObject<ServerContact> onSuccess, final RunnableWithObject<TypeOfServerError> onFailure){
        userApi.getContactById(id).enqueue(new Callback<ServerContact>() {
            @Override
            public void onResponse(Call<ServerContact> call, Response<ServerContact> response) {

                check_error(response.code(), onFailure);

                if (response.body()!=null) {
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ServerContact> call, Throwable t) {
                onFailure.init(TypeOfServerError.INTERNET_DOES_NOT_WORK);
                onFailure.run();
            }
        });
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
