package com.example.android.concatadapter

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
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

    fun get(): ConcatAdapter {
        val concatAdapter = ConcatAdapter()
        if (config.showBanner) {
            concatAdapter.addAdapter(BannerAdapter(""))
        }
        concatAdapter.addAdapter(this)
        if (config.showNative) {
            concatAdapter.addAdapter(NativeAdapter(""))
        }
        return concatAdapter
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
        if (viewType == TYPE_ADS) {
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
        when (holder) {
            is NativeViewHolder -> {
                holder.bindNativeViewHolder(holder, position)
            }
            else -> bindItemViewHolder(holder as Y, position)
        }
    }


    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): Y
    abstract fun bindItemViewHolder(holder: Y, position: Int)

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
        return if (listData[position] == null) TYPE_ADS else TYPE_ITEM
    }

    companion object {
        const val TYPE_ITEM = 2
        const val TYPE_ADS = 3
    }
}

class BannerAdapter(adsKey: String) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}

class NativeAdapter(adsKey: String) : RecyclerView.Adapter<NativeViewHolder>() {
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

    override fun getItemCount(): Int {
        return 1
    }
}

class NativeViewHolder(binding: ItemNativeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindNativeViewHolder(holder: NativeViewHolder, position: Int) {

    }
}