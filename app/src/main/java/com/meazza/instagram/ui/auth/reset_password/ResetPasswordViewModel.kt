package com.meazza.instagram.ui.auth.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.meazza.instagram.common.callback.StatusCallback
import com.meazza.instagram.data.network.AuthService
import com.meazza.instagram.util.EMPTY_FIELDS
import com.meazza.instagram.util.INVALID_EMAIL
import com.meazza.instagram.util.USER_NOT_FOUND
import com.meazza.instagram.util.isValidEmail
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val authRepository: AuthService) : ViewModel() {

    var statusCallback: StatusCallback? = null
    var emailToResetPassword = MutableLiveData<String>()

    fun resetPassword() {

        val email = emailToResetPassword.value

        viewModelScope.launch {
            if (!email.isNullOrEmpty()) {
                try {
                    when {
                        !isValidEmail(email) -> statusCallback?.onFailure(INVALID_EMAIL)
                        isValidEmail(email) -> {
                            authRepository.resetPassword(email)
                            statusCallback?.onSuccess()
                        }
                    }
                } catch (e: FirebaseAuthInvalidUserException) {
                    statusCallback?.onFailure(USER_NOT_FOUND)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                statusCallback?.onFailure(EMPTY_FIELDS)
            }
        }
    }
}
