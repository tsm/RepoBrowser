package com.tomszom.repobrowser.repository

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.UserRepositoriesQuery
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.repository_item.*

class RepositoryViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {
        const val LAYOUT_ID = R.layout.repository_item
    }

    fun bind(model: UserRepositoriesQuery.Node) {
        repositoryItemName.text = model.name()
        repositoryItemUrl.text = model.url() as String
    }

}