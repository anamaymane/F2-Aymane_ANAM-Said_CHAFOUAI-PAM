package com.example.newsapp.model

data class NewsApiResponse(val status: String, val totalResults: Int, val articles: List<NewsItem>) {
}