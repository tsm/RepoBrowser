package com.tomszom.repobrowser.owner

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.OwnerQuery
import com.tomszom.repobrowser.core.domain.UseCase
import com.tomszom.repobrowser.core.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

open class GetOwnersUseCase @Inject constructor(
    private val apolloClient: ApolloClient,
    private val schedulerProvider: SchedulerProvider
) : UseCase<List<String>, List<OwnerQuery.RepositoryOwner>>() {

    companion object {
        const val TAG = "GetOwnersUseCase"
    }

    override fun getObservable(param: List<String>): Observable<List<OwnerQuery.RepositoryOwner>> {
        return if (param.isEmpty()) {
            Observable.fromCallable { emptyList<OwnerQuery.RepositoryOwner>() }
        } else {
            val query = OwnerQuery.builder().login(param.first()).build() //TODO get more than one

            val apolloCall = apolloClient.query(query)

            Rx2Apollo.from<OwnerQuery.Data>(apolloCall).map { mapResponseToOwner(it, param.first()) }.toList()
                .toObservable()
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler())
        }
    }

    private fun mapResponseToOwner(response: Response<OwnerQuery.Data>?, login: String): OwnerQuery.RepositoryOwner {
        return response?.data()?.repositoryOwner() ?: OwnerQuery.RepositoryOwner("User", "", login)
    }
}