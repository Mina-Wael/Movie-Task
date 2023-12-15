package com.example.movietask.data.local

import androidx.room.TypeConverter
import com.example.movietask.domain.pojo.ResultPojo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GenreIdConverter {

    @TypeConverter
    fun convertListToString(list: List<Int>): String = Gson().toJson(list)

    @TypeConverter
    fun convertStringToList(string: String): List<Int> =
        Gson().fromJson(string, object : TypeToken<List<Int>>() {}.type)
}