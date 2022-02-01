package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsDataViewModel : ViewModel() {
    val newsData = MutableLiveData<List<NewsItem>>()

    fun getNewData(): MutableLiveData<List<NewsItem>> {
        return newsData
    }
}