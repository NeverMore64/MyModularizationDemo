package com.example.basemodule.network;

import com.example.basemodule.utils.log.Log;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class GsonConverterFactory extends Converter.Factory {

    public static final String TAG = GsonConverterFactory.class.getSimpleName();

    private final Gson gson;

    public static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }

    private GsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson==null");
        this.gson = gson;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(
            Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new GsonRequestBodyConverter<>();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson, type);
    }

    private static final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        private final Gson gson;
        private final Type type;

        public GsonResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                String json = value.string();
                ResponseError responseError = gson.fromJson(json, ResponseError.class);
                String data = gson.toJson(responseError.getData());
                Log.json(TAG, json);
                T t = gson.fromJson(data, type);
                if (responseError.getError() != 10000) {
                    throw new ApiException(responseError.getError(), responseError.getMessage());
                } else if (t == null) {
                    throw new ApiNullException();
                }
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                value.close();
            }
        }
    }

    private static class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

        public GsonRequestBodyConverter() {
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, value.toString());
        }
    }

}
