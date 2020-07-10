package com.meazza.instagram.ui.add_post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnImageClickListener
import com.meazza.instagram.util.squareResize
import kotlinx.android.synthetic.main.layout_image.view.*

class ImageAdapter(
    private val list: MutableList<String>,
    private val listener: OnImageClickListener
) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder =
        ImageHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_image, parent, false)
        )

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = if (list.size > 0) list.size else 0

    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String) = itemView.run {
            iv_image.squareResize(url, 300)
            setOnClickListener {
                listener.onClick(url)
            }
        }
    }
}