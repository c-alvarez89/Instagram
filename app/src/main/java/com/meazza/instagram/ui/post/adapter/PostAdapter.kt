package com.meazza.instagram.ui.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnPostClickListener
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.LayoutPostBinding

class PostAdapter(private val listener: OnPostClickListener) :
    RecyclerView.Adapter<PostAdapter.HolderPost>() {

    private var postList = mutableListOf<Post>()

    fun setList(posts: MutableList<Post>) {
        postList = posts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPost =
        HolderPost(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_post,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HolderPost, position: Int) {
        holder.itemBinding.run {
            post = postList[position]
            root.setOnClickListener {
                listener.onClickPost(postList[position])
            }
        }
    }

    override fun getItemCount(): Int = if (postList.size > 0) postList.size else 0

    inner class HolderPost(val itemBinding: LayoutPostBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}