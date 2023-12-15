package com.example.movietask.domain.pojo

data class TrendingMoviesRespose(
    val page: Int,
    val results: List<ResultPojo>,
    val total_pages: Int,
    val total_results: Int
)