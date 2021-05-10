package com.pedro.latihanapi.Network;

import com.pedro.latihanapi.Model.CoronaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpoint {

    @GET("public/api/prov.json")
    Call<CoronaModel> getData(
            @Query("indonesia/") String indonesia,
            @Query("provinsi") String provinsi

    );

}
