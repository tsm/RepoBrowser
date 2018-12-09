package com.tomszom.repobrowser.owner

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tomszom.repobrowser.OwnerQuery
import javax.inject.Inject

class OwnerAdapter @Inject constructor() : RecyclerView.Adapter<OwnerViewHolder>() {

    var onOwnerClick: (String) -> Unit = {}

    var ownerList: List<OwnerQuery.RepositoryOwner> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerViewHolder {
        val inflated = LayoutInflater.from(parent.context).inflate(OwnerViewHolder.LAYOUT_ID, parent, false)
        return OwnerViewHolder(inflated)
    }

    override fun getItemCount() = ownerList.size


    override fun onBindViewHolder(holder: OwnerViewHolder, position: Int) {
        holder.bind(ownerList[position], onOwnerClick)
    }

}