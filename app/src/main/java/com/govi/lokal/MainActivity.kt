package com.govi.lokal

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.govi.lokal.adapter.MyAdapter
import com.govi.lokal.api.ApiService
import com.govi.lokal.databinding.ActivityMainBinding
import com.govi.lokal.model.MyData
import com.govi.lokal.model.Product
import io.supercharge.shimmerlayout.ShimmerLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var dataList: ArrayList<Product>
    lateinit var shimmerLayout: ShimmerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Here, implementation of shimmer effect.
         */
        shimmerLayout = binding.shimmerLayout
        shimmerLayout.startShimmerAnimation()

        /**
         * Initialize the dataList
         */
        dataList = ArrayList()

        /**
         * Here setting up recycler view in android layout.
         */
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /**
         * Here, set up our adapter with an empty list of data.
         */
        myAdapter = MyAdapter(this, dataList){}

        /**
         *Below code is responsible for managing the data and creating views for the items in the RecyclerView.
         */
        binding.recyclerView.adapter = myAdapter

        /**
         * Calling getMyData() to fetch data from API
         */
        getMyData()

    }

    /**
     * Here, making a network request to fetch data from a provided API
     */
    private fun getMyData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val retrofitData = retrofit.fetchData()

        retrofitData.enqueue(object: Callback<MyData>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                val responseBody = response.body()
                Log.d("MyResponse",response.body().toString());
                Log.d("MyResponse",response.toString());
                if(responseBody != null){
                    dataList.addAll(responseBody.products)
                    myAdapter.notifyDataSetChanged()
                    shimmerLayout.isInvisible = true
                    binding.recyclerView.isVisible = true
                    Log.d("MyResponse","Data successfully fetched inside responseBody");
                }
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d("MyResponse","onFailure"+t.message)
            }

        })
    }

}