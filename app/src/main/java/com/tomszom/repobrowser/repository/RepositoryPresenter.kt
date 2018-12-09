package com.tomszom.repobrowser.repository

import com.tomszom.repobrowser.BuildConfig
import com.tomszom.repobrowser.core.presentation.BasePresenter
import javax.inject.Inject

class RepositoryPresenter @Inject constructor(
    val view: RepositoryContract.View,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
) : BasePresenter(), RepositoryContract.Presenter {

    override fun onResume() {
        super.onResume()
        refreshAction()
    }

    override fun refreshAction() {
        getUserRepositoriesUseCase.getObservable(view.getGitUser())
            .doOnSubscribe {
                view.showProgress()
                view.hideEmpty()
            }
            .doOnTerminate { view.hideProgress() }
            .subscribe(
                { list ->
                    view.showRepositories(list.reversed())
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
            ).addWeakDisposable(GetUserRepositoriesUseCase.TAG)
    }
}