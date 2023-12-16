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

class RepositoryImpl @Inject constructor(var local: MovieDao, var remote: RetrofitServices) :
    RepositoryIntr {
    override fun getRemoteMovies(): Flow<Resource<List<ResultPojo>>> = flow {
        emit(Resource.Loading)
        try {
            var res = remote.getTrendingMovies().body()
            emit(Resource.Success(res!!.results))

        } catch (ex: HttpException) {
            emit(Resource.Failed("Un expected error"))
        } catch (ex: IOException) {
            emit(Resource.Failed("Check your network connection"))
        }
    }

    override suspend fun getSavedMovies(): List<ResultPojo> = local.getAllSavedMovies()

    override suspend fun saveMovies(movieList: List<ResultPojo>) {
        local.insertMovie(movieList)
    }
}