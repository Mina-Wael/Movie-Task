package com.example.movietask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movietask.domain.pojo.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreIdConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getDao(): MovieDao

}