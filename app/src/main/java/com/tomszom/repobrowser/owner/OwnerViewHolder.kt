package com.tomszom.repobrowser.owner

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tomszom.repobrowser.OwnerQuery
import com.tomszom.repobrowser.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.owner_row.*

class OwnerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {
        const val LAYOUT_ID = R.layout.owner_row
    }

    fun bind(model: OwnerQuery.RepositoryOwner, onOwnerClick: (String) -> Unit) {

        ownerItemLogin.text = model.login()
        loadAvatar(model.avatarUrl() as String)

        containerView.setOnClickListener { onOwnerClick.invoke(model.login()) }
    }

    private fun loadAvatar(avatarUrl: String) {
        val ownerIconId = R.drawable.ic_owner
        if (avatarUrl.isBlank()) {
            ownerItemAvatar.setImageResource(ownerIconId)
        } else {
            Glide.with(containerView.context)
                .load(avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(ownerIconId)
                        .error(ownerIconId)
                        .fitCenter()
                )
                .into(ownerItemAvatar)
        }
    }

}