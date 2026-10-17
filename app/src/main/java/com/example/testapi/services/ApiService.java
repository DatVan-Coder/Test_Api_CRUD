package com.example.testapi.services;

import com.example.testapi.models.HoaDon;
import com.example.testapi.models.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    public static String BASE_URl = "http://10.0.2.2:3001/";
    @GET("hoadon/list")
    Call<Response<ArrayList<HoaDon>>> getListUser();

    @POST("hoadon/add")
    Call<Response<HoaDon>> addHoaDon(@Body HoaDon hoadon);

    @PUT("hoadon/update/{id}")
    Call<Response<HoaDon>> updateHoaDon(@Path("id") String id, @Body HoaDon hoadon);

    @DELETE("hoadon/delete/{id}")
    Call<Response<Void>> deleteHoaDon(@Path("id") String id);

}
