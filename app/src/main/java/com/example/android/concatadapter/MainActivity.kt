package com.example.android.concatadapter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AdsAdapter<String, *>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)


        val adapter = UserAdapter(config = AdsAdapterConfig(4,
            showAdsInCenter = true,
            showNative = true,
        loadMore = false)).apply {


            onclickEvent = {
                deleteItem(it)
                Toast.makeText(this@MainActivity, "addItem", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter.get()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0 && (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= (recyclerView.layoutManager as GridLayoutManager).itemCount) {
//                    if (!isLoading) {
//                        loadNextPage(adapter)
//                    }
//                }
            }
        })
        recyclerView.layoutManager = AdsGridLayoutManager(this, 3, adapter.get())

        adapter.setData(listOf(
            "thinh1",
            "thinh2",
            "thinh3",
            "thinh4",
            "thinh5",
            "thinh6",
            "thinh7",
            "thinh8",
            "thinh9",
            "thinh10",
            "thinh11",
            "thinh12",
            "thinh13",

        ))
    }

    var isLoading = false
    private fun loadNextPage(adapter: UserAdapter) {
        isLoading = true
        adapter.loadMore()
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.addData(listOf("thinh", "thinh"))
            isLoading = false
        }, 3000)
    }

    private companion object {
        private const val VISIBLE_THRESHOLD = 3
    }
}