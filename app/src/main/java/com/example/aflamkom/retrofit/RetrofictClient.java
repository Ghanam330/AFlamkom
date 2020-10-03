package com.example.aflamkom.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofictClient {

private static final String BASE_URL= "https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/api/";

public static ApiInterface getRetrofitClient(){
    Retrofit.Builder builder=new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .baseUrl(BASE_URL);
    return builder.build().create(ApiInterface.class);


}



/*
    public static ApiInterface getService(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BASE_URL);



        return builder.build().create(ApiInterface.class);


    }

 */




/*
public static ApiInterface getService(){
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    return retrofit.create(ApiInterface.class);
}

 */
}
