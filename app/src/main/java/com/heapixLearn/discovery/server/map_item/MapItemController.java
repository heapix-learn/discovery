package com.heapixLearn.discovery.server.map_item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapItemController {
    static final String BASE_URL = "http://localhost:8008/";
    private static MapItemApi mapItemService;

    public static MapItemApi getApi(final String token){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", token)
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient.build());


        Retrofit retrofit = builder
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mapItemService = retrofit.create(MapItemApi.class);
        return mapItemService;
    }
}
