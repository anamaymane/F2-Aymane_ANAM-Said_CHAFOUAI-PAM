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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.model.Communicator
import com.example.newsapp.model.NewsItem
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflater = inflater.inflate(R.layout.item_details_fragment, container, false)

        println("beginning fragment details")

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
            println("url: ${newsItem.urlToImage}")
            println("message from fragment ${newsItem.author}")
        })

        return inflater
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }


            }
    }
}