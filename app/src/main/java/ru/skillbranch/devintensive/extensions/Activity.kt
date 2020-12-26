package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

//скрывает клавиатуру при нажатии на кнопку
fun Activity.hideKeyboard( ) {
    val view = this.currentFocus

    if (view != null) {
        val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideMe.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
fun String.isLettersOnly() = all { it.isLetter() }



