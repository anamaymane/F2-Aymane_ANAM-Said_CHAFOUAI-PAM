package com.example.newsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.model.NewsItem

@Dao
interface NewsItemDAO {

    @Query("SELECT * FROM news_item")
    fun getAll(): List<NewsItem>

    @Query("DELETE FROM news_item")
    fun deleteAll()

    @Insert
    @JvmSuppressWildcards
    fun insertAll(news_items: List<NewsItem>)

}