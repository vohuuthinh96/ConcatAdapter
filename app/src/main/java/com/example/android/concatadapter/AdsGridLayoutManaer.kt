package com.example.android.concatadapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager


/**
 * Created by Thinhvh on 20/10/2022.
 * Phone: 0398477967
 * Email: thinhvh.fpt@gmail.com
 */
class AdsGridLayoutManager(context: Context, spanCount: Int, concatAdapter: ConcatAdapter) :
    GridLayoutManager(context, spanCount) {
    init {
       spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(concatAdapter.getItemViewType(position)) {
                    AdsAdapter.TYPE_CENTER_NATIVE, AdsAdapter.TYPE_ITEM -> 1
                    else -> spanCount
                }
            }
        }
    }
}