package com.meazza.instagram.common.listener

interface StatusCallback {
    fun onSuccess()
    fun onFailure(messageCode: Int)
}