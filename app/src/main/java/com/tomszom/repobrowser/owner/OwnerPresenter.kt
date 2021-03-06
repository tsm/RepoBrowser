package com.tomszom.repobrowser.owner

import com.tomszom.repobrowser.BuildConfig
import com.tomszom.repobrowser.core.presentation.BasePresenter
import javax.inject.Inject

class OwnerPresenter @Inject constructor(
    val view: OwnerContract.View,
    private val getOwnersUseCase: GetOwnersUseCase
) : BasePresenter(), OwnerContract.Presenter {

    val owners = mutableListOf("tsm", "github")

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
                    view.showOwners(list.sortedBy { it.login() })
                    if (list.isEmpty()) {
                        view.showEmpty()
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

    override fun addOwner(newOwner: String) {
        if (newOwner.isNotBlank() && !owners.contains(newOwner)) {
            owners.add(newOwner)
            refreshAction()
        }
        view.resetNewOwnerInput()
    }
}