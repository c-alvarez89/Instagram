package com.meazza.instagram.ui.auth

interface AuthListener {
    fun onSuccess()
    fun onFailure(messageCode: Int)
}