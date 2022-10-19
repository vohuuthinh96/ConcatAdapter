package com.example.android.concatadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(config = AdsAdapterConfig(5)).apply {
            setData(listOf("thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
                ,"thinh"
            ))

            onclickEvent = {
                addItem("value")
                Toast.makeText(this@MainActivity, "addItem",Toast.LENGTH_SHORT).show()
            }
        }.get()
        recyclerView.adapter = adapter
    }
}