package com.meazza.instagram.ui.user_profile.edit_profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.api.load
import coil.transform.CircleCropTransformation
import com.meazza.instagram.R
import com.meazza.instagram.common.Permissions
import com.meazza.instagram.databinding.FragmentEditProfileBinding
import com.meazza.instagram.listener.UiActionListener
import com.meazza.instagram.vo.PermissionState
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.koin.android.ext.android.inject

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile), UiActionListener {

    companion object {
        const val GALLERY_REQUEST = 101
    }

    private val editProfileViewModel by inject<EditProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<FragmentEditProfileBinding>(view)?.apply {
            lifecycleOwner = this@EditProfileFragment
            viewModel = editProfileViewModel
        }

        editProfileViewModel.uiActionListener = this
        setHasOptionsMenu(true)
        setToolbar()
    }

    private fun setToolbar() {
        val activity = activity as AppCompatActivity
        activity.apply {
            setSupportActionBar(tb_edit_profile)
            title = "Edit Profile"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            try {
                when (requestCode) {
                    GALLERY_REQUEST -> {
                        val image = data?.data
                        iv_change_user_photo.load(image) {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun setUiActionFromViewModel() {
        when (Permissions(activity as Activity).checkAllPermissions()) {
            PermissionState.ALL_PERMISSIONS_GRANTED -> {
                startActivityForResult(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ), GALLERY_REQUEST
                )
            }
            PermissionState.DENIED -> {
            }
            PermissionState.GRANTED -> {
            }
            PermissionState.PERMANENTLY_DENIED -> {
            }
            else -> {
            }
        }
    }
}
