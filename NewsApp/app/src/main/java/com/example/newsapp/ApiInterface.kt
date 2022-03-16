package com.example.newsapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.newsapp.database.NewsItemDatabase
import com.example.newsapp.model.NewsDataViewModel
import com.example.newsapp.model.NewsItem
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiInterface {

    companion object {

        @JvmStatic
        val url: String =
            "https://newsapi.org/v2/everything?q=tesla&sortBy=publishedAt&apiKey=9454289c0f374c4f8f3532dc89cde4ed"

        lateinit var context: Context    //Context for interacting with room

        //The error is that the fuel method is synchronous not asynchronous
        fun getNews(liveData: NewsDataViewModel): Unit {
            val newsItems: MutableList<NewsItem> = ArrayList<NewsItem>()

            FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Mozilla/5.0")
            Log.i("fuel", "fuel request")
            url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException().exception
                            Log.e("result", "bool ${ex.javaClass.kotlin.qualifiedName}")
                            if (ex is UnknownHostException || ex is SocketTimeoutException) {
                                Log.e("error", "Socket timeout exception")

                                // If there is no internet connection
                                // We restore the last data that was
                                // stored in the database when the
                                // internet were still accessible
                                val db = NewsItemDatabase(context)
                                newsItems.addAll(db.newsItemDAO().getAll())
                                liveData.getNewData().postValue(newsItems)
                            }

                        }
                        is Result.Success -> {
                            Log.i("success", "Result success")
                            val jsonObject = JSONTokener(result.get()).nextValue() as JSONObject
                            val articles = jsonObject.getJSONArray("articles")

                            val articlesLength = articles.length() - 1
                            (1..articlesLength).iterator().forEach {

                                val article: JSONObject = articles[it] as JSONObject

                                val newItem: NewsItem = NewsItem(
                                    it.toLong(),
                                    article["author"].toString(),
                                    article["title"].toString(),
                                    article["description"].toString(),
                                    article["url"].toString(),
                                    article["urlToImage"].toString(),
                                    article["publishedAt"].toString(),
                                    article["content"].toString()
                                )
                                newsItems.add(newItem)
                            }

                            liveData.getNewData().postValue(newsItems)

                            GlobalScope.launch {
                                val db = NewsItemDatabase(context)
                                db.newsItemDAO().deleteAll()
                                db.newsItemDAO().insertAll(newsItems)
                            }
                        }
                    }
                }
        }

    }
}