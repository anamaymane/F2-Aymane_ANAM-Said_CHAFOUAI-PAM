package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.model.NewsDataViewModel
import com.example.newsapp.model.NewsItem
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.json.JSONObject
import org.json.JSONTokener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsList.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsList : Fragment() {
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
        val inflater = inflater.inflate(R.layout.fragment_news_list, container, false)

        // getting the recyclerview by its id
        val recyclerview = inflater.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(activity)


        var data : List<NewsItem> = ArrayList<NewsItem>()

        // This loop will create 20 Views contaiing
        // the image with the count of view


        // This will pass the ArrayList to our Adapter
        val adapter = NewsAdapter(data, this)
        recyclerview.adapter = adapter

        val newsDataViewModel = NewsDataViewModel()

        newsDataViewModel.getNewData().observe(viewLifecycleOwner, Observer {
                println("Call back succeeded")
                recyclerview.adapter = NewsAdapter(it, this)
            }
        )

        ApiInterface.context = requireContext()    //The context is needed by Room
        ApiInterface.getNews(newsDataViewModel)

        return inflater
    }



    companion object {
        const val id:Int = 555
    }
}