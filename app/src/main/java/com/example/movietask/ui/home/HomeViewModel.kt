package com.example.movietask.ui.home

import androidx.lifecycle.ViewModel
import com.example.movietask.domain.repository.RepositoryIntr
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var repo:RepositoryIntr) : ViewModel() {
}