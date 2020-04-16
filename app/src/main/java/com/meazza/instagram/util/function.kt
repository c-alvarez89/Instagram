package com.meazza.instagram.util

import android.util.Patterns
import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val patternPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{6,}\$"
    val pattern = Pattern.compile(patternPassword)
    return pattern.matcher(password).matches()
}
