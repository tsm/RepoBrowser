package com.tomszom.repobrowser.owner

import com.tomszom.repobrowser.OwnerQuery
import com.tomszom.repobrowser.core.presentation.BaseContract

interface OwnerContract {

    interface View : BaseContract.View {
        fun showProgress()
        fun hideProgress()
        fun showError()
        fun showEmpty()
        fun hideEmpty()
        fun showOwners(list: List<OwnerQuery.RepositoryOwner>)
        fun startRepositoryActivity(login: String)
    }

    interface Presenter : BaseContract.Presenter {
        fun refreshAction()
        fun onOwnerClick(login: String)
    }
}