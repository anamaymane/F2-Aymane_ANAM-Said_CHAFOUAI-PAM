package com.example.newsapp

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
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

    private var recyclerview: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val inflater = inflater.inflate(R.layout.fragment_news_list, container, false)


        // getting the recyclerview by its id
        recyclerview = inflater.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview!!.layoutManager = LinearLayoutManager(activity)

        var data : List<NewsItem> = ArrayList<NewsItem>()

        // This loop will create 20 Views contaiing
        // the image with the count of view


        // This will pass the ArrayList to our Adapter
        val adapter = NewsAdapter(data, this)
        recyclerview!!.adapter = adapter

        val newsDataViewModel = NewsDataViewModel()

        newsDataViewModel.getNewData().observe(viewLifecycleOwner, Observer {

            val progressBar: ProgressBar = inflater.findViewById(R.id.spinner_news)
            progressBar.visibility = View.GONE

                if(it.size == 0) {
                    val noIntenetNoDbMessage: TextView = inflater.findViewById(R.id.textView_no_internet_no_db)
                    noIntenetNoDbMessage.visibility = View.VISIBLE
                }
                else {
                    recyclerview!!.adapter = NewsAdapter(it, this)
                    if(mBundleRecyclerViewState != null)
                        restoreRecyclerState()
                }

            }
        )

        ApiInterface.context = requireContext()    //The context is needed by Room
        ApiInterface.getNews(newsDataViewModel)
        return inflater
    }

    override fun onPause() {
        super.onPause()

        // Saving the state of the list in a static bunde
        // so that we can restore the list state on rotation
        mBundleRecyclerViewState = Bundle();
        val mListState = recyclerview?.getLayoutManager()?.onSaveInstanceState();
        mBundleRecyclerViewState!!.putParcelable(RECYCLER_VIEW_STATE_KEY, mListState);
    }

    fun restoreRecyclerState() {

        // Restoring the list state to handle
        // rotation use case

        Handler().postDelayed(Runnable {
            val mListState: Parcelable? = mBundleRecyclerViewState!!.getParcelable(RECYCLER_VIEW_STATE_KEY)
            if(mListState != null)
                recyclerview?.getLayoutManager()?.onRestoreInstanceState(mListState)
            }, 50)
    }



    companion object {

        const val id:Int = 555

        @JvmStatic
        var mBundleRecyclerViewState: Bundle? = null

        @JvmStatic
        var RECYCLER_VIEW_STATE_KEY: String = "RECYCLER_VIEW_STATE_KEY"
    }
}