package com.example.movietask.di

import android.app.Application
import androidx.room.Room
import com.example.movietask.data.local.MovieDao
import com.example.movietask.data.local.MovieDatabase
import com.example.movietask.data.remote.RetrofitServices
import com.example.movietask.data.repository.RepositoryImpl
import com.example.movietask.domain.repository.RepositoryIntr
import com.example.movietask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DaggerModule {

    @Provides
    fun provideRetrofitSerVices(): RetrofitServices =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitServices::class.java)

    @Provides
    fun provideMovieDao(context: Application): MovieDao =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            Constants.DATABASE_NAME
        ).build().getDao()

    @Provides
    fun provideRepo(local: MovieDao, remote: RetrofitServices): RepositoryIntr =
        RepositoryImpl(local, remote)
}