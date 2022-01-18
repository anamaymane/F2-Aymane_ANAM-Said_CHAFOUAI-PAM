package com.example.newsapp

import com.example.newsapp.model.NewsDataViewModel
import com.example.newsapp.model.NewsItem
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.json.JSONObject
import org.json.JSONTokener

class ApiInterface {

    companion object {
        @JvmStatic
        val url: String =
            "https://newsapi.org/v2/everything?q=tesla&sortBy=publishedAt&apiKey=9454289c0f374c4f8f3532dc89cde4ed"

        //The error is that the fuel method is synchronous not asynchronous
        suspend fun getNews() : ArrayList<NewsItem>  {
            val newsItems: ArrayList<NewsItem> = ArrayList<NewsItem>()

            FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Mozilla/5.0")

            url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
//                        val temp : String = result.get()
                            val jsonObject = JSONTokener(result.get()).nextValue() as JSONObject
                            val articles = jsonObject.getJSONArray("articles")

                            val articlesLength = articles.length() - 1
                            (1..articlesLength).iterator().forEach {
                                val article: JSONObject = articles[it] as JSONObject

                                val newItem: NewsItem = NewsItem(
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


                        }
                    }
                }
            return newsItems
        }
    }


}