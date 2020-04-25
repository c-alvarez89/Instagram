package com.meazza.instagram.ui

interface StatusListener {
    fun onSuccess()
    fun onFailure(messageCode: Int)
}