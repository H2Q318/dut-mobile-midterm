package com.midterm.hoangquanghung.viewmodel;

import com.midterm.hoangquanghung.model.Data;
import com.midterm.hoangquanghung.model.DataApi;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApiService {
    public static final String BASE_URL = "https://api.jsonbin.io/";
    private DataApi api;

    public DataApiService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(DataApi.class);
    }
    public Single<List<Data>> getData() {
        return api.getData();
    }
}
