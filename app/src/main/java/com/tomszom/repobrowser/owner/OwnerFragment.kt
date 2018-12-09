package com.tomszom.repobrowser.owner

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tomszom.repobrowser.OwnerQuery
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.extension.gone
import com.tomszom.repobrowser.core.extension.onImeDone
import com.tomszom.repobrowser.core.extension.visible
import com.tomszom.repobrowser.core.presentation.BaseFragment
import com.tomszom.repobrowser.repository.RepositoryActivity
import kotlinx.android.synthetic.main.owner_fragment.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject


class OwnerFragment : BaseFragment<OwnerContract.Presenter>(), OwnerContract.View {

    @Inject
    lateinit var ownerAdapter: OwnerAdapter

    @Inject
    override lateinit var presenter: OwnerContract.Presenter

    override fun getLayoutId() = R.layout.owner_fragment
    override fun getViewId() = "OwnerFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showOwnerListTitle()
        setupRecycler()
        setupOnClicks()
    }

    private fun setupOnClicks() {
        ownerAdapter.onOwnerClick = presenter::onOwnerClick
        ownerAddButton.setOnClickListener { presenter.addOwner(ownerInput.text.toString()) }
        ownerInput.onImeDone(presenter::addOwner)
    }

    private fun showOwnerListTitle() {
        getBaseActivity().title = getString(R.string.owner_list_title)
    }

    private fun setupRecycler() {
        ownerList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        ownerList.adapter = ownerAdapter
    }

    override fun showEmpty() {
        ownerEmpty?.visible()
    }

    override fun hideEmpty() {
        ownerEmpty?.gone()
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

    override fun showOwners(list: List<OwnerQuery.RepositoryOwner>) {
        ownerAdapter.ownerList = list
    }

    override fun resetNewOwnerInput() {
        ownerInput.text = null
        ownerSeparator.requestFocus()
        getBaseActivity().hideKeyboard()
    }

    override fun startRepositoryActivity(login: String) {
        getBaseActivity().run {
            startActivity(
                getBaseActivity().intentFor<RepositoryActivity>()
                    .putExtra(RepositoryActivity.EXTRA_LOGIN, login)
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
