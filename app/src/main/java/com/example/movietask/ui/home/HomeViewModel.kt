package com.example.movietask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietask.domain.pojo.Movie
import com.example.movietask.domain.pojo.MovieReponse
import com.example.movietask.domain.repository.RepositoryIntr
import com.example.movietask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var repo: RepositoryIntr) : ViewModel() {

    init {
        getRemoteMovie()
    }

    private var _movieStateFlow: MutableStateFlow<Resource<List<Movie>>> =
        MutableStateFlow(Resource.Loading)
    var movieStateFlow: StateFlow<Resource<List<Movie>>> = _movieStateFlow

    private val _searchLiveData = MutableLiveData<Resource<List<Movie>>>()
    val searchLiveData: LiveData<Resource<List<Movie>>> = _searchLiveData

    var searchJob: Job? = null

    private fun getRemoteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllMovies().collect {
                _movieStateFlow.emit(it)
            }
        }
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            repo.search(query).collect {
                _searchLiveData.postValue(it)
            }
        }
    }
}