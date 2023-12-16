package com.example.movietask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietask.domain.pojo.ResultPojo
import com.example.movietask.domain.repository.RepositoryIntr
import com.example.movietask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var repo: RepositoryIntr) : ViewModel() {

    init {
        getRemoteMovie()
    }

    private var _movieStateFlow: MutableStateFlow<Resource<List<ResultPojo>>> =
        MutableStateFlow(Resource.Loading)
    var movieStateFlow: StateFlow<Resource<List<ResultPojo>>> = _movieStateFlow

    fun getRemoteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRemoteMovies().collect {
                _movieStateFlow.emit(it)
            }
        }
    }

}