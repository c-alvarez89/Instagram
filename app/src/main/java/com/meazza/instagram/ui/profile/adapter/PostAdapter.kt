package com.meazza.instagram.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.LayoutProfilePostBinding

class PostAdapter(private val listener: PostListener) :
    RecyclerView.Adapter<PostAdapter.ProfilePostHolder>() {

    private var postList = mutableListOf<Post>()

    fun setList(posts: MutableList<Post>) {
        postList = posts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostHolder =
        ProfilePostHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_profile_post,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProfilePostHolder, position: Int) {
        holder.itemBinding.run {
            post = postList[position]
            root.setOnClickListener {
                listener.onClickPost(postList[position])
            }
        }
    }

    override fun getItemCount(): Int = if (postList.size > 0) postList.size else 0

    inner class ProfilePostHolder(val itemBinding: LayoutProfilePostBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}