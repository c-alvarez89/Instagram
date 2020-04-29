package com.meazza.instagram.ui.user_profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.meazza.instagram.R
import com.meazza.instagram.common.OnItemClickListener
import com.meazza.instagram.common.permission.PermissionRequest
import com.meazza.instagram.common.permission.PermissionState
import com.meazza.instagram.databinding.FragmentEditProfileBinding
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream


@Suppress("NAME_SHADOWING", "DEPRECATION")
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile),
    OnItemClickListener {

    companion object {
        const val GALLERY_REQUEST_CODE = 101
    }

    private val profileViewModel by inject<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentEditProfileBinding>(view)?.apply {
            lifecycleOwner = this@EditProfileFragment
            viewModel = profileViewModel
        }

        profileViewModel.onClickListener = this
        setHasOptionsMenu(true)
        setToolbar()

        profileViewModel.getUser()
    }

    private fun setToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(tb_edit_profile)
            title = getString(R.string.edit_profile)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    private fun pickImageFromGallery() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            GALLERY_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    val imageUri = data?.data

                    iv_change_user_photo.load(imageUri) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }

                    try {
                        imageUri?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    activity?.contentResolver,
                                    imageUri
                                )
                                val stream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                                val byteArray = stream.toByteArray()
                                profileViewModel.uploadImage(byteArray)
                            } else {
                                val source = activity?.contentResolver?.let { contentResolver ->
                                    ImageDecoder.createSource(
                                        contentResolver,
                                        imageUri
                                    )
                                }
                                val bitmap =
                                    source?.let { source -> ImageDecoder.decodeBitmap(source) }
                                val stream = ByteArrayOutputStream()
                                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                                val byteArray = stream.toByteArray()
                                profileViewModel.uploadImage(byteArray)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mn_save_changes) {
            profileViewModel.saveChanges()
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickFromViewModel() {
        when (PermissionRequest(activity as Activity)
            .allPermissions()) {
            PermissionState.ALL_PERMISSIONS_GRANTED -> {
                pickImageFromGallery()
            }
            PermissionState.DENIED -> {
            }
            PermissionState.PERMANENTLY_DENIED -> {
            }
        }
    }
}
