package com.tomszom.repobrowser.repository

import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.presentation.BaseContract

interface RepositoryContract {

    interface View : BaseContract.View {
        fun getGitUser(): String
        fun getToken(): String

        fun showProgress()
        fun hideProgress()
        fun showError()
        fun showEmpty()
        fun hideEmpty()
        fun showRepositories(list: List<UserRepositoriesQuery.Node>)
    }

    interface Presenter : BaseContract.Presenter {
        fun refreshAction()
    }
}