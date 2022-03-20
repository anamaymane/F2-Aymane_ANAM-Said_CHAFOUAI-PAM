package com.example.newsapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.model.Communicator
import com.example.newsapp.model.NewsItem

class NewsAdapter(private var mList: List<NewsItem>, private val context: Fragment) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

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
        holder.news_item_title.text = newsItem.title

        val endIndex = if (newsItem.description.length > 90) 90 else newsItem.description.length
        holder.news_item_description.text = newsItem.description.substring(0, endIndex) + "..."

        holder.news_item_date.text = newsItem.publishedAt.substring(0, 10)

        holder.card.setOnClickListener(View.OnClickListener {
            // Code here executes on main thread after user presses button
            //Toast.makeText(activity?.applicationContext, R.string.toast_description, Toast.LENGTH_SHORT).show()
//            switchToSecondoActivity(it)
            println("item details fragment")

            val communicator = ViewModelProviders.of(context.requireActivity())
                .get(Communicator::class.java)

            communicator.setMessage(newsItem)

            context.findNavController().navigate(R.id.itemDetailsFragment)
        });


        Glide.with(context)
            .load(newsItem.urlToImage)
            .error(R.mipmap.not_found_image)
            .into(holder.imageNews);

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val news_item_title: TextView = itemView.findViewById(R.id.news_item_title)
        val news_item_date: TextView = itemView.findViewById(R.id.news_item_date)
        val news_item_description: TextView = itemView.findViewById(R.id.news_item_description)
        val imageNews: ImageView = itemView.findViewById(R.id.news_item_image)
        val card: View = itemView
    }
}