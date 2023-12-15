package com.example.movietask.utils

sealed class Resource<out R> {
    class Success<out T>(val data: T) : Resource<T>()
    class Failed(val message: String) : Resource<Nothing>()
    object EmptyOrNUll : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}