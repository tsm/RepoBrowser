package com.tomszom.repobrowser.repository

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.extension.gone
import com.tomszom.repobrowser.core.extension.visible
import com.tomszom.repobrowser.core.presentation.BaseFragment
import kotlinx.android.synthetic.main.repository_fragment.*
import javax.inject.Inject


class RepositoryFragment : BaseFragment<RepositoryContract.Presenter>(), RepositoryContract.View {

    private val repositoryAdapter = RepositoryAdapter()

    @Inject
    override lateinit var presenter: RepositoryContract.Presenter

    override fun getLayoutId() = R.layout.repository_fragment
    override fun getViewId() = "RepositoryFragment"

    override fun getGitUser() = "tsm" //TODO get it from bundle/argument

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRepositoryListTitle(getGitUser())
        setupRecycler()
    }

    private fun showRepositoryListTitle(owner: String) {
        getBaseActivity().title = String.format(getString(R.string.repository_list_title), owner)
    }

    private fun setupRecycler() {
        repositoryList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        repositoryList.adapter = repositoryAdapter
    }

    override fun showEmpty() {
        repositoryEmpty?.visible()
    }

    override fun hideEmpty() {
        repositoryEmpty?.gone()
    }

    override fun showProgress() {
        getBaseActivity().showProgress()
    }

    override fun hideProgress() {
        getBaseActivity().hideProgress()
    }

    override fun showError() {
        getBaseActivity().notifyWithAction(
            R.string.common_error,
            R.string.common_action_retry
        ) { presenter.refreshAction() }
    }

    override fun showRepositories(list: List<UserRepositoriesQuery.Node>) {
        repositoryAdapter.repositoryList = list
    }
}
