package com.meazza.instagram.ui.direct_message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnUserClickListener
import com.meazza.instagram.data.model.User
import com.meazza.instagram.databinding.LayoutUserDirectBinding

class DirectAdapter(val listener: OnUserClickListener) :
    RecyclerView.Adapter<DirectAdapter.HolderUserDirect>() {

    private var userList = mutableListOf<User>()

    fun setUserList(users: MutableList<User>) {
        userList = users
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HolderUserDirect(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_user_direct,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HolderUserDirect, position: Int) {
        holder.itemBinding.run {
            user = userList[position]
            root.setOnClickListener {
                listener.onItemClickListener(userList[position])
            }
        }
    }

    override fun getItemCount(): Int = if (userList.size > 0) userList.size else 0

    inner class HolderUserDirect(val itemBinding: LayoutUserDirectBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}