package com.example.basemodule.network.modules;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class DoubleTypeAdapter implements JsonSerializer<Double> {
    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        Locale locale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        DecimalFormat format = new DecimalFormat("##0.000000");
        String temp = format.format(src);
        JsonPrimitive pri = new JsonPrimitive(temp);
        Locale.setDefault(locale);
        return pri;
    }
}
