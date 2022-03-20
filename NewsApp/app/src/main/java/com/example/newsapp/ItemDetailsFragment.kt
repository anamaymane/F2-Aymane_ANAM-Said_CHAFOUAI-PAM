package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.newsapp.model.Communicator
import com.example.newsapp.model.NewsItem


/**
 * A simple [Fragment] subclass.
 * Use the [ItemDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflater = inflater.inflate(R.layout.item_details_fragment, container, false)

        val communicator = ViewModelProviders.of(requireActivity())
            .get(Communicator::class.java)

        val message = communicator.newsItem.observe(viewLifecycleOwner, Observer<Any> {
            val newsItem : NewsItem = it as NewsItem;
            val title = inflater.findViewById<TextView>(R.id.textView_title)
            val date = inflater.findViewById<TextView>(R.id.textView_date)
            val author = inflater.findViewById<TextView>(R.id.textView_author)
            val description = inflater.findViewById<TextView>(R.id.textView_description)
            val newsImage = inflater.findViewById<ImageView>(R.id.news_image)
            val newsContent = inflater.findViewById<TextView>(R.id.textView_content)

            title.text = newsItem.title
            date.text = newsItem.publishedAt.substring(0, 10)
            author.text = if(newsItem.author != "null") newsItem.author else "Author unknown"
            description.text = newsItem.description
            newsContent.text = newsItem.content
            Glide.with(this)
                .load(newsItem.urlToImage)
                .error(R.mipmap.not_found_image)
                .into(newsImage);
        })

        return inflater
    }

}