package com.example.newsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator: ViewModel() {
    val newsItem = MutableLiveData<Any>()

    fun setMessage(newsItem:Any)
    {
        this.newsItem.postValue(newsItem)
    }


}