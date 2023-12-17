package com.example.movietask.domain.repository

import com.example.movietask.domain.pojo.ResultPojo
import com.example.movietask.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RepositoryIntr {

    fun getAllMovies(): Flow<Resource<List<ResultPojo>>>

}