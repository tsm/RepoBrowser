package com.tomszom.repobrowser.core.presentation

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.extension.gone
import com.tomszom.repobrowser.core.extension.visible
import kotlinx.android.synthetic.main.base_content.*

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
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

    internal fun showProgress() = baseProgress.visible()

    internal fun hideProgress() = baseProgress.gone()
}