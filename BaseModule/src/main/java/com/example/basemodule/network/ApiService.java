package com.example.basemodule.network;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public interface ApiService {

    @POST("/erp/api/v20/test")
    Observable<String> getTestData();

}
