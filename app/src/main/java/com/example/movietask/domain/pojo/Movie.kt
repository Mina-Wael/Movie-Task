package com.example.movietask.domain.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    @PrimaryKey val id: Int,
    val media_type: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
):Serializable{
    override fun hashCode(): Int {
        var result = id.hashCode()
        if(poster_path.isNullOrEmpty()){
            result = 31 * result + poster_path.hashCode()
        }
        return result
    }
}