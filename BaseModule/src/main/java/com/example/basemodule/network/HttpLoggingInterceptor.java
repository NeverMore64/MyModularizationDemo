package com.example.basemodule.network;


import com.example.basemodule.utils.log.Log;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class HttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = setHeader(request.newBuilder());
//        if (DebugUtils.isChangeHostOpen()!!) {
//            try {
//                val newUrl = request.url().newBuilder()
//                        .host(DebugUtils.getChangeHost())
//                        .port(DebugUtils.getChangePort())
//                        .build()
//                builder.url(newUrl)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }

        String url = request.url().toString();
        if (url.contains("xxx")) {          // 替换host
            HttpUrl newUrl = request.url().newBuilder().host("").build();
            builder.url(newUrl);
        }
        // 替换host示例：
        //        val url = request.url().toString()
//        if (url.contains("/erp/api/v20/venue/checkverifycode") || url.contains("/erp/api/v20/venue/checkin")) {
//            try {
//                val newUrl = request.url().newBuilder().host(BuildConfig.BASE_VENUE_URL).build()
//                builder.url(newUrl)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
        Request newRequest = builder.build();
        RequestBody requestBody = newRequest.body();
        boolean hasRequestBody = requestBody != null;
        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "-->" + newRequest.method() + "''" + newRequest.url() + "''" + protocol;
        Log.d("HTTP QUEST", requestStartMessage);
        if (hasRequestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Log.json("HTTP QUEST", buffer.readUtf8());
        }

        Response response;
        try {
            response = chain.proceed(newRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HTTP FAILED", e.getMessage());
            ResponseError responseError = new ResponseError();
            if (e instanceof SocketTimeoutException) {
                // 超时
                Log.e("HTTP FAILED", "SocketTimeoutException");
                responseError.setError(500);
                responseError.setMessage("网络请求超时");
                throw new ApiException(responseError.getError(), responseError.getMessage());
            } else if (e instanceof SocketException) {
                if (e.getMessage() != null && !e.getMessage().contains("Socket closed")) {
                    Log.e("HTTP FAILED", "Socket closed");
                    responseError.setError(500);
                    responseError.setMessage("请检查您的网络设置");
                    throw new ApiException(responseError.getError(), responseError.getMessage());
                }
            } else if (e instanceof IOException) {
                if (!e.getMessage().contains("Canceled")) {
                    Log.e("HTTP FAILED", "Canceled");
                    responseError.setError(666);
                    responseError.setMessage("请检查您的网络设置");
                    throw new ApiException(responseError.getError(), responseError.getMessage());
                }
            }
            throw e;
        }

        if (response.code() < 200 || response.code() > 299) {
            ResponseError error = new ResponseError();
            error.setError(response.code());
            Log.e("HTTP FAILED",  "response code is "+response.code());
            if (response.code() == 403) {
                throw new ProtocolException("失效异常");
            } else {
                error.setMessage("服务器开小差");
                throw new ApiException(error.getError(), error.getMessage());
            }
        }
        return response;
    }


    private Request.Builder setHeader(Request.Builder params) {
        params.header("Accept", "application/json");
        params.header("Content-Type", "application/json");
        return params;
    }

}
