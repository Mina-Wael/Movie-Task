package com.example.movietask.domain.pojo

data class MovieReponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)