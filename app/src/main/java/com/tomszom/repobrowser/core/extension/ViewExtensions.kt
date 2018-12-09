package com.tomszom.repobrowser.core.extension

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun EditText.onImeDone(listener: (String) -> Unit) {
    this.setOnEditorActionListener { textView, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener.invoke(textView.text.toString())
            true
        } else {
            false
        }
    }
}
