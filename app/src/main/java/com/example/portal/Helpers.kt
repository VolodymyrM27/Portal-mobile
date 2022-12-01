package com.example.portal

import android.content.Context
import android.widget.Toast

fun processError(context: Context, msg: String?) {
    showToast(context, "Error:$msg")
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
