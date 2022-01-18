package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsDataViewModel : ViewModel() {
    val newsData : MutableLiveData<List<NewsItem>> by lazy {
        MutableLiveData<List<NewsItem>>()
    }
}