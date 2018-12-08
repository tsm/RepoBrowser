package com.tomszom.repobrowser.core.presentation

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun layoutId(): Int

    private fun getBaseActivity() = activity as BaseActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId(), container, false)

    protected fun notify(@StringRes message: Int) =
        getBaseActivity().notify(message)

    protected fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) =
        getBaseActivity().notifyWithAction(message, actionText, action)

    protected fun showProgress() = getBaseActivity().showProgress()

    protected fun hideProgress() = getBaseActivity().hideProgress()

}