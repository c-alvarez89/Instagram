package com.meazza.instagram.ui.add_post.filter

import com.zomato.photofilters.utils.ThumbnailItem

interface FilterListener {
    fun onClickItem(item: ThumbnailItem)
}