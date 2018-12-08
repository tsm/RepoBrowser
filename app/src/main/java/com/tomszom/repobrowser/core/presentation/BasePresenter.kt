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

    protected fun Disposable.addWeakDisposable(key: String) {
        disposeWithKey(weakDisposables, key)
        weakDisposables[key] = this
    }

    protected fun Disposable.addPersistDisposable(key: String) {
        disposeWithKey(persistDisposables, key)
        persistDisposables[key] = this
    }

    private fun disposeWithKey(disposables: HashMap<String, Disposable>, key: String) {
        val disposable = disposables.remove(key)
        if (disposable?.isDisposed == false) {
            disposable.dispose()
        }
    }
}