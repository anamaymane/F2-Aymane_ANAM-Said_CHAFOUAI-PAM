package com.example.newsapp.model

data class Source(val id: Int?, val name: String);

data class NewsItem(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
    ) {

}