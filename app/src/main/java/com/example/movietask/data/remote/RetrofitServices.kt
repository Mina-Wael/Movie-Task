package com.example.movietask.data.remote

import com.example.movietask.domain.pojo.MovieReponse
import com.example.movietask.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitServices {


    @GET("trending/movie/day?language=en-US")
    @Headers(Constants.ACCESS_TOKEN, "accept:application/json")
    suspend fun getTrendingMovies(): Response<MovieReponse>

    @GET("search/movie")
    @Headers(Constants.ACCESS_TOKEN, "accept:application/json")
    suspend fun search(
        @Query("query") movieName: String
    ): Response<MovieReponse>
}