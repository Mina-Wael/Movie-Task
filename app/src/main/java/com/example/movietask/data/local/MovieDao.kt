package com.example.movietask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietask.domain.pojo.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<Movie>)

    @Query("Select * from Movie")
    suspend fun getAllSavedMovies(): List<Movie>


}