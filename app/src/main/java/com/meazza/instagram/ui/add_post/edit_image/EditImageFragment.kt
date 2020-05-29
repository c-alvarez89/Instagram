package com.meazza.instagram.ui.add_post.edit_image

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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meazza.instagram.R
import com.meazza.instagram.common.listener.OnFilterClickListener
import com.meazza.instagram.data.model.EditedImage
import com.meazza.instagram.util.load
import com.meazza.instagram.util.setToolbar
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.android.synthetic.main.fragment_edit_image.*


@Suppress("DEPRECATION", "NAME_SHADOWING")
class EditImageFragment : Fragment(R.layout.fragment_edit_image),
    OnFilterClickListener {

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
        setFilters()
        setRecyclerView()
        setHasOptionsMenu(true)
        setToolbar(activity, tb_filter, "")
    }

    private fun getBitmap() {
        imageString = arguments?.let { EditImageFragmentArgs.fromBundle(it).imageUri }
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

        val mAdapter = FilterThumbnailAdapter(this@EditImageFragment)

        mAdapter.apply {
            setListData(filterList)
            notifyDataSetChanged()
        }

        itemAnimator = DefaultItemAnimator()
        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        adapter = mAdapter
        setHasFixedSize(true)
    }

    private fun gotoCreatePost() {
        val imagePost = EditedImage(imageString, filterName)
        val action = EditImageFragmentDirections.gotoNewPost(imagePost)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_next, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_next) gotoCreatePost()
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
