package com.example.newsapp

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.HttpException
import com.example.newsapp.model.NewsItem

class NewsAdapter(private var mList: List<NewsItem>, private val context: Fragment) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    fun setNewsItems(mList: List<NewsItem>) {
        this.mList = mList
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val newsItem = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = newsItem.title
        try {
            Glide.with(context).load(newsItem.urlToImage).into(holder.imageNews);
        }
        catch(exception: HttpException) {
            print("Glide catch")
            holder.imageNews.setImageResource(R.drawable.not_found_image_background)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.news_item_title)
        val imageNews: ImageView = itemView.findViewById(R.id.news_item_image)
    }
}