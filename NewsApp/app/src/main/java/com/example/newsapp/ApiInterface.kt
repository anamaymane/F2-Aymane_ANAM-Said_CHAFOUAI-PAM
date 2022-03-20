package com.example.newsapp

import android.content.Context
import com.example.newsapp.database.NewsItemDatabase
import com.example.newsapp.model.NewsDataViewModel
import com.example.newsapp.model.NewsItem
import io.ktor.client.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ApiInterface {

    companion object {

        @JvmStatic
        val url: String =
            "https://newsapi.org/v2/everything?q=tesla&sortBy=publishedAt&apiKey=9454289c0f374c4f8f3532dc89cde4ed"

        lateinit var context: Context    //Context for interacting with room

        val ktorHttpClient = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 2500
                connectTimeoutMillis = 1500
                socketTimeoutMillis = 1500
            }
        }

        //The error is that the fuel method is synchronous not asynchronous
        fun getNews(liveData: NewsDataViewModel): Unit {

            val newsItems: MutableList<NewsItem> = ArrayList<NewsItem>()

            val job = GlobalScope.launch {

                try{
                    val httpResponse: HttpResponse = ktorHttpClient.request(url) {
                        method = HttpMethod.Get
                    }

                    if (httpResponse.status.value in 200..299) {
                        val content = httpResponse.readText()
                        val jsonObject = JSONTokener(content).nextValue() as JSONObject
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
                catch(ex: Exception) {
                    // If there is no internet connection
                    // We restore the last data that was
                    // stored in the database when the
                    // internet were still accessible
                    val db = NewsItemDatabase(context)
                    newsItems.addAll(db.newsItemDAO().getAll())
                    liveData.getNewData().postValue(newsItems)
                }
            }
        }
    }
}