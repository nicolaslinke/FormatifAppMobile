package com.example.formatifappmobile_1.http;

import com.example.formatifappmobile_1.objects.number;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("exam/representations/{nombre}")
    Call<List<number>> getNumber(@Path("nombre") String nombre);
}
