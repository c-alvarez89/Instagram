package com.meazza.instagram.ui.add_post.filter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.meazza.instagram.R
import com.meazza.instagram.ui.add_post.adapter.FilterThumbnailAdapter
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.android.synthetic.main.fragment_filter.*


@Suppress("DEPRECATION", "NAME_SHADOWING")
class FilterFragment : Fragment(R.layout.fragment_filter), FilterListener {

    private val filterList = ArrayList<ThumbnailItem>()
    private var imageString: String? = null
    private var filterName: String? = null
    private var originalImage: Bitmap? = null
    private var newImage: Bitmap? = null

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBitmap()
        setToolbar()
        setFilters()
        setRecyclerView()
    }

    private fun getBitmap() {
        imageString = arguments?.let { FilterFragmentArgs.fromBundle(it).imageUri }
        val imageUri = Uri.parse(imageString)
        try {
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver,
                        imageUri
                    )
                    originalImage = bitmap
                    iv_preview_image.load(originalImage)
                } else {
                    val source = activity?.contentResolver?.let { contentResolver ->
                        ImageDecoder.createSource(
                            contentResolver,
                            imageUri
                        )
                    }
                    val bitmap =
                        source?.let { source -> ImageDecoder.decodeBitmap(source) }
                    originalImage = bitmap
                    iv_preview_image.load(originalImage)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setToolbar() {
        val mActivity = activity as AppCompatActivity
        setHasOptionsMenu(true)
        mActivity.apply {
            setSupportActionBar(tb_filter)
            supportActionBar?.run {
                setHomeAsUpIndicator(R.drawable.ic_arrow_left)
                setDisplayHomeAsUpEnabled(true)
            }
        }
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

    private fun setRecyclerView() = rv_filters.run {

        val mAdapter = FilterThumbnailAdapter(this@FilterFragment)

        mAdapter.apply {
            setListData(filterList)
            notifyDataSetChanged()
        }

        itemAnimator = DefaultItemAnimator()
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        adapter = mAdapter
        setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_next, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_next) {
            val imagePost = FilterImage(imageString, filterName)
            val action = FilterFragmentDirections.gotoNewPost(imagePost)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickItem(item: ThumbnailItem) {
        val copyImage = originalImage?.copy(originalImage?.config, true)
        val filter = item.filter
        filterName = item.filterName
        newImage = filter.processFilter(copyImage)
        iv_preview_image.load(newImage)
    }
}
