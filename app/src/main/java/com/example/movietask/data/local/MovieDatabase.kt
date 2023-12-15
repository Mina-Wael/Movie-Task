package com.example.movietask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movietask.domain.pojo.ResultPojo

@Database(entities = [ResultPojo::class], version = 1)
@TypeConverters(GenreIdConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getDao(): MovieDao

}