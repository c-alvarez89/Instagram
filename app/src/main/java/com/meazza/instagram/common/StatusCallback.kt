package com.meazza.instagram.common

interface StatusCallback {
    fun onSuccess()
    fun onFailure(messageCode: Int)
}