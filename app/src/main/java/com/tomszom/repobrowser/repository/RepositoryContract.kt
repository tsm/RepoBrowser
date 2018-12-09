package com.tomszom.repobrowser.repository

import com.tomszom.repobrowser.RepositoriesQuery
import com.tomszom.repobrowser.core.presentation.BaseContract

interface RepositoryContract {

    interface View : BaseContract.View {
        fun getOwnerLogin(): String

        fun showProgress()
        fun hideProgress()
        fun showError()
        fun showEmpty()
        fun hideEmpty()
        fun showRepositories(list: List<RepositoriesQuery.Node>)
    }

    interface Presenter : BaseContract.Presenter {
        fun refreshAction()
    }
}