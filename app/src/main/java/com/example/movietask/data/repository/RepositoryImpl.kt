package com.example.movietask.data.repository

import com.example.movietask.data.local.MovieDao
import com.example.movietask.data.remote.RetrofitServices
import com.example.movietask.domain.pojo.ResultPojo
import com.example.movietask.domain.repository.RepositoryIntr
import com.example.movietask.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    var local: MovieDao,
    private var remote: RetrofitServices
) :
    RepositoryIntr {
    override fun getAllMovies(): Flow<Resource<List<ResultPojo>>> = flow {
        emit(Resource.Loading)
        try {
            var res = remote.getTrendingMovies().body()
            saveMovies(res!!.results)

        } catch (ex: HttpException) {
            emit(Resource.Failed("Un expected error"))
        } catch (ex: IOException) {
            emit(Resource.Failed("Check your network connection"))
        }
        val movieList = getSavedMovies()
        if (movieList.isEmpty()) {
            emit(Resource.EmptyOrNUll("No data to display"))
        } else
            emit(Resource.Success(movieList))
    }

    private suspend fun getSavedMovies(): List<ResultPojo> = local.getAllSavedMovies()

    private suspend fun saveMovies(movieList: List<ResultPojo>) {
        local.insertMovie(movieList)
    }
}