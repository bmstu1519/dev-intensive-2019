package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard( ) {
    val view = this.currentFocus

    if (view != null) {
        val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideMe.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
