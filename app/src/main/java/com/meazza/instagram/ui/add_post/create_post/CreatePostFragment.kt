package com.meazza.instagram.ui.add_post.create_post

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.meazza.instagram.R
import com.meazza.instagram.databinding.FragmentCreatePostBinding
import com.meazza.instagram.util.setToolbarWithBackArrow
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import kotlinx.android.synthetic.main.fragment_create_post.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

@Suppress("NAME_SHADOWING", "DEPRECATION")
class CreatePostFragment : Fragment(R.layout.fragment_create_post) {

    private val createPostViewModel by inject<CreatePostViewModel>()

    private var originalImage: Bitmap? = null
    private var filterName: String? = null
    private var newImage: Bitmap? = null
    private var copyImage: Bitmap? = null

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentCreatePostBinding>(view)?.apply {
            lifecycleOwner = this@CreatePostFragment
            viewModel = createPostViewModel
        }

        createPostViewModel.getCurrentUser()

        getBitmap()
        setThumbnail()
        setHasOptionsMenu(true)
        setToolbarWithBackArrow(activity, tb_new_post, getString(R.string.new_post))
    }

    private fun getBitmap() {
        val imagePost = arguments?.let { CreatePostFragmentArgs.fromBundle(it).editedImage }
        val imageUri = Uri.parse(imagePost?.image)
        filterName = imagePost?.filter
        try {
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver,
                        imageUri
                    )
                    originalImage = bitmap
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
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setFilter(): Filter? {
        return when (filterName) {
            "Struck" -> FilterPack.getAweStruckVibeFilter(context)
            "Clarendon" -> FilterPack.getClarendon(context)
            "OldMan" -> FilterPack.getOldManFilter(context)
            "Mars" -> FilterPack.getMarsFilter(context)
            "Rise" -> FilterPack.getRiseFilter(context)
            "April" -> FilterPack.getAprilFilter(context)
            "Amazon" -> FilterPack.getAmazonFilter(context)
            "Starlit" -> FilterPack.getStarLitFilter(context)
            "Whisper" -> FilterPack.getNightWhisperFilter(context)
            "Lime" -> FilterPack.getLimeStutterFilter(context)
            "Haan" -> FilterPack.getHaanFilter(context)
            "BlueMess" -> FilterPack.getBlueMessFilter(context)
            "Adele" -> FilterPack.getAdeleFilter(context)
            "Cruz" -> FilterPack.getCruzFilter(context)
            "Metropolis" -> FilterPack.getMetropolis(context)
            "Audrey" -> FilterPack.getAudreyFilter(context)
            else -> null
        }
    }

    private fun setThumbnail() {
        copyImage = originalImage?.copy(originalImage?.config, true)
        if (setFilter() != null) {
            val filter = setFilter()
            newImage = filter?.processFilter(copyImage)
            iv_new_post.load(newImage)
        } else {
            iv_new_post.load(copyImage)
        }
    }

    private fun addImagePost() {
        val stream = ByteArrayOutputStream()
        copyImage?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        createPostViewModel.addImagePost(byteArray)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_share, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_share) {
            addImagePost()
            findNavController().navigate(
                R.id.navigation_feed, null,
                NavOptions.Builder().setPopUpTo(R.id.nav_main, true).build()
            )
        }
        return super.onOptionsItemSelected(item)
    }
}
