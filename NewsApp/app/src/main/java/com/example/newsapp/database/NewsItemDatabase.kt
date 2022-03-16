package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.model.NewsItem

@Database(entities = arrayOf(NewsItem::class), version = 1)
abstract class NewsItemDatabase: RoomDatabase() {

    abstract fun newsItemDAO(): NewsItemDAO

    companion object {

        @Volatile private var instance: NewsItemDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            NewsItemDatabase::class.java, "news-item.db")
            .build()
    }

}