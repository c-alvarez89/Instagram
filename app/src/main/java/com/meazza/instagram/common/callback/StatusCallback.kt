package com.meazza.instagram.common.callback

interface StatusCallback {
    fun onSuccess()
    fun onFailure(messageCode: Int)
}