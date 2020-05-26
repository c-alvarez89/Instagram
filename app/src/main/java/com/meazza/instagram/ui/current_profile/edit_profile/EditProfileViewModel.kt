package com.meazza.instagram.ui.current_profile.edit_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meazza.instagram.common.callback.StatusCallback
import com.meazza.instagram.common.listener.OnViewClickListener
import com.meazza.instagram.data.network.CurrentUserDB
import com.meazza.instagram.util.TRY_AGAIN
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userInstance: CurrentUserDB) : ViewModel() {

    var onClickListener: OnViewClickListener? = null
    var statusCallback: StatusCallback? = null

    var name = MutableLiveData<String>()
    var username = MutableLiveData<String>()
    var photoUrl = MutableLiveData<String>()
    var bio = MutableLiveData<String>()
    var website = MutableLiveData<String>()
    var posts = MutableLiveData<String>()
    var followers = MutableLiveData<String>()
    var following = MutableLiveData<String>()

    fun changeUserPhoto() {
        onClickListener?.onClickFromViewModel()
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                val user = userInstance.getUser()
                name.value = user?.name
                username.value = user?.username
                photoUrl.value = user?.photoUrl
                bio.value = user?.bio
                website.value = user?.website
                posts.value = user?.postNumber.toString()
                followers.value = user?.followersNumber.toString()
                following.value = user?.followingNumber.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            try {
                userInstance.updateUser(
                    name.value!!,
                    username.value!!,
                    bio.value!!,
                    website.value!!
                )
            } catch (e: Exception) {
                statusCallback?.onFailure(TRY_AGAIN)
                e.printStackTrace()
            }
        }
    }

    fun uploadImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                userInstance.uploadPhoto(imageUri)
            } catch (e: Exception) {
                statusCallback?.onFailure(TRY_AGAIN)
                e.printStackTrace()
            }
        }
    }
}
