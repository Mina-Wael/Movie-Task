package com.example.movietask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietask.domain.pojo.ResultPojo

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<ResultPojo>)

    @Query("Select * from ResultPojo")
    suspend fun getAllSavedMovies(): List<ResultPojo>


}