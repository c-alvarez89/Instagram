package com.meazza.instagram.ui.user_profile.edit_profile

import androidx.lifecycle.ViewModel
import com.meazza.instagram.ui.UiActionListener

class EditProfileViewModel : ViewModel() {

    var uiActionListener: UiActionListener? = null

    fun changeUserPhoto() {
        uiActionListener?.setUiActionFromViewModel()
    }
}
