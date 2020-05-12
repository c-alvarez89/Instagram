package com.meazza.instagram.ui.add_post.filter

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.meazza.instagram.R
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager


class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val filterList = ArrayList<ThumbnailItem>()
    private var originalImage: Bitmap? = null

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        originalImage = arguments?.let { FilterFragmentArgs.fromBundle(it).image }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilters()
    }

    private fun setFilters() {

        filterList.clear()
        ThumbnailsManager.clearThumbs()

        val normal = ThumbnailItem().apply {
            image = originalImage
            filterName = "Normal"
        }
        ThumbnailsManager.addThumb(normal)

        val filters = FilterPack.getFilterPack(context)

        for (filterItem in filters) {
            val filterThumb = ThumbnailItem().apply {
                image = originalImage
                filter = filterItem
                filterName = filterItem.name
            }
            ThumbnailsManager.addThumb(filterThumb)
        }

        filterList.addAll(ThumbnailsManager.processThumbs(context))
    }
}

