package com.meazza.instagram.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.LayoutUserFoundBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.HolderUserFound>() {

    private var dataList = mutableListOf<User>()

    fun setData(data: MutableList<User>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HolderUserFound(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_user_found,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SearchAdapter.HolderUserFound, position: Int) {
        holder.itemBinding.user = dataList[position]
    }

    override fun getItemCount(): Int = if (dataList.size > 0) dataList.size else 0

    inner class HolderUserFound(val itemBinding: LayoutUserFoundBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}