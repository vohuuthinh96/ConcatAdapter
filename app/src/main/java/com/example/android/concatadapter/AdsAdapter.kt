package com.example.android.concatadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TableRow
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.concatadapter.AdsAdapter.Companion.TYPE_BOTTOM_NATIVE
import com.example.android.concatadapter.AdsAdapter.Companion.TYPE_HEADER_BANNER
import com.example.android.concatadapter.databinding.ItemBannerBinding
import com.example.android.concatadapter.databinding.ItemNativeBinding


/**
 * Created by Thinhvh on 19/10/2022.
 * Phone: 0398477967
 * Email: thinhvh.fpt@gmail.com
 */
abstract class AdsAdapter<T, Y : RecyclerView.ViewHolder>(private var config: AdsAdapterConfig) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listData = mutableListOf<T?>()
    private lateinit var adapter: ConcatAdapter
    private var loadMoreAdapter = LoadMoreAdapter()

    fun get(): ConcatAdapter {
        if (!this::adapter.isInitialized) {
            adapter =
                ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())
            if (config.showBanner) {
                adapter.addAdapter(BannerAdapter(""))
            }
            adapter.addAdapter(this)
            if (config.showNative) {
                adapter.addAdapter(NativeAdapter(""))
            }

            if (config.loadMore) {
                adapter.addAdapter(loadMoreAdapter)
            }
        }

        return adapter
    }

    fun deleteItem(index: Int) {
        listData.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, listData.size)
    }

    fun updateItem(index: Int, item: T) {
        listData[index] = item
        notifyItemChanged(index)
    }

    fun addItem(item: T) {
        listData.add(item)
        notifyItemInserted(listData.size)
    }

    fun addData(data: List<T>) {
        removeLoadMore()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        this.listData.clear()
        notifyDataSetChanged()
    }

    fun setData(data: List<T>) {
        this.listData.clear()
        this.listData.addAll(getDataWithAdsItem(data))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_CENTER_NATIVE) {
            return NativeViewHolder(
                ItemNativeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NativeViewHolder) {
            holder.bindNativeViewHolder(holder, position)
        } else if (isInstanceOf(holder)) {
            bindItemViewHolder(holder as Y, position - listData.subList(0, position).filter { it == null }.size)
        }
    }


    private inline fun <reified Y> isInstanceOf(y: Y): Boolean = when (Y::class) {
        Y::class -> true
        else -> false
    }


    private fun getDataWithAdsItem(data: List<T>): Collection<T?> {
        val listData = mutableListOf<T?>()
        if (config.showAdsInCenter) {
            data.forEachIndexed { index, appData ->
                if (index > 0 && index % config.itemThresholds == 0) {
                    listData.add(null)
                }
                listData.add(appData)
            }
        } else {
            this.listData.addAll(data)
        }
        return listData
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position in listData.indices) {
            return if (listData[position] == null) TYPE_CENTER_NATIVE else TYPE_ITEM
        } else return TYPE_ITEM

    }

    fun loadMore() {
        loadMoreAdapter.loadState = LOAD_STATE.LOADING
        loadMoreAdapter.notifyItemChanged(0)
    }

    private fun removeLoadMore() {
        loadMoreAdapter.loadState = LOAD_STATE.SUCCESS
        loadMoreAdapter.notifyItemChanged(0)
    }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): Y
    abstract fun bindItemViewHolder(holder: Y, position: Int)

    open fun getLoadMoreViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ProgressBar(parent.context)
        return LoadMoreViewHolder(view)
    }

    open fun bindLoadMore(holder: RecyclerView.ViewHolder, position: Int) {

    }

    companion object {
        const val TYPE_ITEM = 2
        const val TYPE_CENTER_NATIVE = 3
        const val TYPE_BOTTOM_NATIVE = 4
        const val TYPE_HEADER_BANNER = 5
        const val TYPE_LOAD_MORE = 6
    }


    inner class LoadMoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var loadState = LOAD_STATE.SUCCESS
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return getLoadMoreViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (loadState) {
                LOAD_STATE.LOADING -> holder.itemView.isVisible = true
                LOAD_STATE.ERROR -> holder.itemView.isVisible = false
                LOAD_STATE.SUCCESS -> holder.itemView.isVisible = false
            }
            bindLoadMore(holder, position)
        }

        override fun getItemCount(): Int {
            return 1
        }
    }

    inner class BannerAdapter(adsKey: String) :
        RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

        inner class BannerViewHolder(binding: ItemBannerBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            return BannerViewHolder(
                ItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemViewType(position: Int): Int {
            return TYPE_HEADER_BANNER
        }

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {

        }


        override fun getItemCount(): Int {
            return 1
        }
    }


    inner class NativeAdapter(adsKey: String) : RecyclerView.Adapter<NativeViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): NativeViewHolder {
            return NativeViewHolder(
                ItemNativeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: NativeViewHolder, position: Int) {
            holder.bindNativeViewHolder(holder, position)
        }

        override fun getItemViewType(position: Int): Int {
            return TYPE_BOTTOM_NATIVE
        }

        override fun getItemCount(): Int {
            return 1
        }
    }

    inner class LoadMoreViewHolder(view: View) :
        RecyclerView.ViewHolder(view)
}

class NativeViewHolder(binding: ItemNativeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindNativeViewHolder(holder: NativeViewHolder, position: Int) {

    }
}

enum class LOAD_STATE {
    SUCCESS,
    ERROR,
    LOADING
}