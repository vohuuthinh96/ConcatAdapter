package com.example.android.concatadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.concatadapter.databinding.ItemLoadMoreBinding
import com.example.android.concatadapter.databinding.ItemUserBinding


/**
 * Created by Thinhvh on 19/10/2022.
 * Phone: 0398477967
 * Email: thinhvh.fpt@gmail.com
 */
class UserAdapter(config: AdsAdapterConfig = AdsAdapterConfig()) :
    AdsAdapter<String, UserAdapter.UserViewHolder>(config) {

    var onclickEvent :((Int)-> Unit)?=null

    inner class UserViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindItemViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onclickEvent?.invoke(position)
        }
    }

    override fun getLoadMoreViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LoadMoreViewHolder(ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class LoadMoreViewHolder(binding: ItemLoadMoreBinding): RecyclerView.ViewHolder(binding.root) {

    }
}