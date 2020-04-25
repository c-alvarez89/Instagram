package com.meazza.instagram.ui.user_profile.edit_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.meazza.instagram.listener.UiActionListener

class EditProfileViewModel() : ViewModel() {

    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    var uiActionListener: UiActionListener? = null
    var name = MutableLiveData<String>()

    init {
    }

    fun changeUserPhoto() {
        uiActionListener?.setUiActionFromViewModel()
    }
}
