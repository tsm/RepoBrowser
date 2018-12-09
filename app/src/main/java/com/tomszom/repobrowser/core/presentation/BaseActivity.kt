package com.tomszom.repobrowser.core.presentation

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.extension.gone
import com.tomszom.repobrowser.core.extension.visible
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.base_content.*
import kotlinx.android.synthetic.main.base_toolbar.*


abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        if (baseToolbar != null) {
            setSupportActionBar(baseToolbar)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    internal fun notify(@StringRes message: Int) =
        Snackbar.make(baseContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(baseContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(applicationContext, R.color.colorAccent)
        )
        snackBar.show()
    }

    internal fun showProgress() = baseProgress?.visible()

    internal fun hideProgress() = baseProgress?.gone()

    internal fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}