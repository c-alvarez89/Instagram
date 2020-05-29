package com.meazza.instagram.ui.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.meazza.instagram.R
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.databinding.LayoutPostBinding

class ExploreAdapter(options: FirestorePagingOptions<Post>) :
    FirestorePagingAdapter<Post, ExploreAdapter.ExploreViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExploreViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_post,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int, model: Post) {
        holder.itemBinding.post = model
    }

    inner class ExploreViewHolder(val itemBinding: LayoutPostBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
