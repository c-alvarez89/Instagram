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
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.R
import com.meazza.instagram.data.model.Post
import com.meazza.instagram.ui.add_post.adapter.FilterThumbnailAdapter
import com.meazza.instagram.ui.add_post.share.NewPostViewModel
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.android.synthetic.main.fragment_filter.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION", "NAME_SHADOWING")
class FilterFragment : Fragment(R.layout.fragment_filter), FilterListener {

    private val newPostViewModel by inject<NewPostViewModel>()
    private val userUid by lazy { FirebaseAuth.getInstance().currentUser?.uid }

    private val filterList = ArrayList<ThumbnailItem>()
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
        val image = arguments?.let { FilterFragmentArgs.fromBundle(it).imageUri }
        val imageUri = Uri.parse(image)
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

    private fun addNewPost(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val post = Post(userUid!!)
        newPostViewModel.addImagePost(post, byteArray)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_next, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_next) {
            newImage?.let {
                val thumbnail = Bitmap.createScaledBitmap(it, 128, 128, true)
                val action = FilterFragmentDirections.gotoNewPost(thumbnail)
                findNavController().navigate(action)
                addNewPost(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickItem(item: ThumbnailItem) {
        val image = originalImage?.copy(originalImage?.config, true)
        val filter = item.filter
        newImage = filter.processFilter(image)
        iv_preview_image.load(newImage)
    }
}
