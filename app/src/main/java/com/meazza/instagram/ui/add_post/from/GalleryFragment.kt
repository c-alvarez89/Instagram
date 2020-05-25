package com.meazza.instagram.ui.add_post.from

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.meazza.instagram.R
import com.meazza.instagram.ui.add_post.AddPostContainerDirections
import com.meazza.instagram.ui.profile.edit_profile.EditProfileFragment
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private var imageSelected: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_open_gallery.setOnClickListener {
            pickImageFromGallery()
        }

        setUiAction()
    }

    private fun setUiAction() {
        iv_close_gallery.setOnClickListener {
            findNavController().popBackStack()
        }
        tv_next_gallery.setOnClickListener {
            imageSelected?.let {
                val action = AddPostContainerDirections.gotoFilter(it)
                findNavController().navigate(action)
            }
        }
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
