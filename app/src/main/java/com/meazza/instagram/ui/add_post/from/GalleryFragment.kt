package com.meazza.instagram.ui.add_post.from

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meazza.instagram.R
import com.meazza.instagram.common.customization.GridSpacingItemDecoration
import com.meazza.instagram.common.listener.OnImageClickListener
import com.meazza.instagram.ui.add_post.AddPostContainerDirections
import com.meazza.instagram.ui.add_post.adapter.ImageAdapter
import com.meazza.instagram.ui.current_profile.edit_profile.EditProfileFragment
import com.meazza.instagram.util.load
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    OnImageClickListener {

    private var imageSelected: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiAction()
        setSpinner()
    }

    private fun setUiAction() {

        iv_close_gallery.setOnClickListener {
            findNavController().popBackStack()
        }

        tv_next_gallery.setOnClickListener {
            imageSelected?.let {
                val action = AddPostContainerDirections.actionEditImage(it)
                findNavController().navigate(action)
            }
        }

        rv_gallery.run {
            addItemDecoration(GridSpacingItemDecoration(4, 4, false))
        }
    }

    private fun setUpRecyclerView(directory: String) {
        val urls = getFilePaths(directory)
        val position = urls.size.minus(1)
        showImage(urls[position])
        rv_gallery.adapter = ImageAdapter(urls.asReversed(), this)
    }

    private fun showImage(url: String) {
        iv_image_selected.load(url)
        tv_next_gallery.visibility = View.VISIBLE
    }

    private fun setSpinner() {

        lateinit var directories: ArrayList<String>

        if (Build.VERSION.SDK_INT < 29) {

            val rootDir = Environment.getExternalStorageDirectory().path
            val pictures = "$rootDir/pictures"
            val camera = "$rootDir/DCIM/camera"
            val directoryNames = ArrayList<String>()

            if (getDirectoryPaths(pictures).isNotEmpty()) {
                directories = getDirectoryPaths(pictures)
            }

            for (i in directories.indices) {
                val index = directories[i].lastIndexOf("/")
                val string = directories[i].substring(index).replace("/", "")
                directoryNames.add(string)
            }

            directories.add(camera)

            sp_gallery.adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, directoryNames
            )

            sp_gallery.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    setUpRecyclerView(directories[position])
                }
            }
        }
    }

    private fun getDirectoryPaths(directory: String): ArrayList<String> {
        val pathArray = java.util.ArrayList<String>()
        val file = File(directory)
        val listFiles = file.listFiles()
        for (i in listFiles.indices) {
            if (listFiles[i].isDirectory) {
                pathArray.add(listFiles[i].absolutePath)
            }
        }
        return pathArray
    }

    private fun getFilePaths(directory: String): ArrayList<String> {
        val pathArray = java.util.ArrayList<String>()
        val file = File(directory)
        val listFiles = file.listFiles()
        for (i in listFiles.indices) {
            if (listFiles[i].isFile) {
                pathArray.add(listFiles[i].absolutePath)
            }
        }
        return pathArray
    }

    override fun onClick(url: String) {
        iv_image_selected.load(url)
    }

    private fun pickImageFromGallery() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            EditProfileFragment.GALLERY_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                EditProfileFragment.GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    iv_image_selected.load(imageUri)
                    imageSelected = imageUri.toString()
                }
            }
        }
    }
}
