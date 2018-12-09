package com.tomszom.repobrowser.core.presentation

import android.support.annotation.CallSuper
import io.reactivex.disposables.Disposable

abstract class BasePresenter : BaseContract.Presenter {

    private val weakDisposables = HashMap<String, Disposable>()
    private val persistDisposables = HashMap<String, Disposable>()

    @CallSuper
    override fun onResume() {
    }

    @CallSuper
    override fun onStop() {
        dispose(weakDisposables)
    }

    @CallSuper
    override fun onDestroyView() {
        dispose(persistDisposables)
    }

    private fun dispose(disposables: HashMap<String, Disposable>) {
        disposables.values.filter { !it.isDisposed }.forEach { it.dispose() }
        disposables.clear()
    }

    protected fun Disposable.addWeakDisposable(tag: String) {
        disposeWithTag(weakDisposables, tag)
        weakDisposables[tag] = this
    }

    protected fun Disposable.addPersistDisposable(tag: String) {
        disposeWithTag(persistDisposables, tag)
        persistDisposables[tag] = this
    }

    private fun disposeWithTag(disposables: HashMap<String, Disposable>, tag: String) {
        val disposable = disposables.remove(tag)
        if (disposable?.isDisposed == false) {
            disposable.dispose()
        }
    }
}