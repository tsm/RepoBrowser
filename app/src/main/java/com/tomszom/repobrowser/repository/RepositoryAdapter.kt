package com.tomszom.repobrowser.repository

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tomszom.repobrowser.RepositoriesQuery

class RepositoryAdapter : RecyclerView.Adapter<RepositoryViewHolder>() {

    var repositoryList: List<RepositoriesQuery.Node> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflated = LayoutInflater.from(parent.context).inflate(RepositoryViewHolder.LAYOUT_ID, parent, false)
        return RepositoryViewHolder(inflated)
    }

    override fun getItemCount() = repositoryList.size


    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositoryList[position])
    }

}