package com.example.movietask.data.repository

import com.example.movietask.data.local.MovieDao
import com.example.movietask.data.remote.RetrofitServices
import com.example.movietask.domain.repository.RepositoryIntr
import javax.inject.Inject

class RepositoryImpl @Inject constructor(var local: MovieDao, var remote: RetrofitServices) :
    RepositoryIntr {
}