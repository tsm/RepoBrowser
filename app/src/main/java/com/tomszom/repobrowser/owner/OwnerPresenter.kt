package com.tomszom.repobrowser.owner

import com.tomszom.repobrowser.BuildConfig
import com.tomszom.repobrowser.core.presentation.BasePresenter
import javax.inject.Inject

class OwnerPresenter @Inject constructor(
    val view: OwnerContract.View,
    private val getOwnersUseCase: GetOwnersUseCase
) : BasePresenter(), OwnerContract.Presenter {

    val owners = listOf("tsm")

    override fun onResume() {
        super.onResume()
        refreshAction()
    }

    override fun refreshAction() {
        getOwnersUseCase.getObservable(owners)
            .doOnSubscribe {
                view.showProgress()
                view.hideEmpty()
            }
            .doOnTerminate { view.hideProgress() }
            .subscribe(
                { list ->
                    if (list.isEmpty()) {
                        view.showEmpty()
                    } else {
                        view.showOwners(list)
                    }
                },
                { e ->
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    view.showError()
                }
            ).addWeakDisposable(GetOwnersUseCase.TAG)
    }

    override fun onOwnerClick(login: String) {
        view.startRepositoryActivity(login)
    }
}