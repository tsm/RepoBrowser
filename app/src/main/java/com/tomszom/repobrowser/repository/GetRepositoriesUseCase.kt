package com.tomszom.repobrowser.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.RepositoriesQuery
import com.tomszom.repobrowser.core.domain.UseCase
import com.tomszom.repobrowser.core.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

open class GetRepositoriesUseCase @Inject constructor(
    private val apolloClient: ApolloClient,
    private val schedulerProvider: SchedulerProvider
) : UseCase<String, List<RepositoriesQuery.Node>>() {

    companion object {
        const val TAG = "GetRepositoriesUseCase"
    }

    override fun getObservable(param: String): Observable<List<RepositoriesQuery.Node>> {

        val query = RepositoriesQuery.builder().login(param).build()

        val apolloCall = apolloClient.query(query)

        return Rx2Apollo.from<RepositoriesQuery.Data>(apolloCall).map { mapResponseToList(it) }
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
    }

    private fun mapResponseToList(response: Response<RepositoriesQuery.Data>?): List<RepositoriesQuery.Node> {
        return response?.data()?.repositoryOwner()?.repositories()?.nodes() ?: emptyList()
    }
}