package com.meazza.instagram.listener

interface StatusListener {
    fun onSuccess()
    fun onFailure(messageCode: Int)
}