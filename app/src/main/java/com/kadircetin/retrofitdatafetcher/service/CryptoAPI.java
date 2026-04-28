package com.kadircetin.retrofitdatafetcher.service;

import android.database.Observable;

import com.kadircetin.retrofitdatafetcher.model.CryptoModel;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CryptoAPI {
    @GET("cryptocurrency/listings/latest")
    Observable<CryptoModel.CmcResponse> getLatestCryptos(
            @Header("X-CMC_PRO_API_KEY") String apiKey,
            @Query("start") int start,
            @Query("limit") int limit,
            @Query("convert") String convert
    );
}
