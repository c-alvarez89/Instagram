package com.meazza.instagram.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnUserClickListener
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.LayoutUserFoundBinding

class SearchAdapter(val listener: OnUserClickListener?) :
    RecyclerView.Adapter<SearchAdapter.HolderUserFound>() {

    private var userList = mutableListOf<User>()

    fun setData(users: MutableList<User>) {
        userList = users
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HolderUserFound(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_user_found,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: HolderUserFound, position: Int) {
        holder.itemBinding.run {
            user = userList[position]
            root.setOnClickListener {
                listener?.onItemClickListener(userList[position])
            }
        }
    }

    override fun getItemCount(): Int = if (userList.size > 0) userList.size else 0

    inner class HolderUserFound(val itemBinding: LayoutUserFoundBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}