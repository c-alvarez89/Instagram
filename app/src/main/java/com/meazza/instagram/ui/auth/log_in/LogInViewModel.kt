package com.meazza.instagram.ui.auth.log_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.meazza.instagram.common.listener.StatusCallback
import com.meazza.instagram.data.network.AuthService
import com.meazza.instagram.util.EMPTY_FIELDS
import com.meazza.instagram.util.LOGIN_ERROR
import com.meazza.instagram.util.USER_NOT_FOUND
import com.meazza.instagram.util.WRONG_PASSWORD
import kotlinx.coroutines.launch

class LogInViewModel(private val authRepository: AuthService) : ViewModel() {

    var statusCallback: StatusCallback? = null

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    fun logIn() {

        val email = email.value
        val password = password.value

        viewModelScope.launch {
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                try {
                    authRepository.loginByEmail(email, password)
                    statusCallback?.onSuccess()
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    statusCallback?.onFailure(WRONG_PASSWORD)
                } catch (e: FirebaseAuthInvalidUserException) {
                    statusCallback?.onFailure(USER_NOT_FOUND)
                } catch (e: Exception) {
                    statusCallback?.onFailure(LOGIN_ERROR)
                    e.printStackTrace()
                }
            } else {
                statusCallback?.onFailure(EMPTY_FIELDS)
            }
        }
    }
}
