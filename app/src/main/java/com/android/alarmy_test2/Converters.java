package com.android.alarmy_test2;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {

    // Used by AlarmEntity Boolean[] mDaysOfWeek

    @TypeConverter
    public static Boolean[] fromString(String value) {
        Type listType = new TypeToken<Boolean[]>() {
        }.getType();
        Log.e("Converter: ", "fromString Called");
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromBoolean(Boolean[] list) {
        Gson gson = new Gson();
        Log.e("Converter: ", "fromString Called");
        return gson.toJson(list);
    }

}