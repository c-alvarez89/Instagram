package com.meazza.instagram.ui.auth.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.meazza.instagram.repository.AuthRepository
import com.meazza.instagram.ui.auth.AuthListener
import com.meazza.instagram.util.*
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var authListener: AuthListener? = null

    var userName = MutableLiveData<String>()
    var userEmail = MutableLiveData<String>()
    var userPassword = MutableLiveData<String>()

    fun signUp() {

        val name = userName.value
        val email = userEmail.value
        val password = userPassword.value

        viewModelScope.launch {
            if (!name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                try {
                    when {
                        !isValidEmail(email) -> authListener?.onFailure(INVALID_EMAIL)
                        !isValidPassword(password) -> authListener?.onFailure(INVALID_PASSWORD)
                        isValidEmail(email) && isValidPassword(password) -> {
                            authRepository.signUpByEmail(email, password)
                            authListener?.onSuccess()
                        }
                    }
                } catch (e: FirebaseAuthUserCollisionException) {
                    authListener?.onFailure(EMAIL_ALREADY_EXISTS)
                } catch (e: Exception) {
                    authListener?.onFailure(REGISTRATION_ERROR)
                    e.printStackTrace()
                }
            } else {
                authListener?.onFailure(EMPTY_FIELDS)
            }
        }
    }
}
