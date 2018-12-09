package com.tomszom.repobrowser.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.domain.UseCase
import com.tomszom.repobrowser.core.util.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

open class GetUserRepositoriesUseCase @Inject constructor(
    private val apolloClient: ApolloClient,
    private val schedulerProvider: SchedulerProvider
) : UseCase<String, List<UserRepositoriesQuery.Node>>() {

    companion object {
        const val TAG = "GetUserRepositoriesUseCase"
    }

    override fun getObservable(param: String): Observable<List<UserRepositoriesQuery.Node>> {

        val query = UserRepositoriesQuery.builder().user_login(param).build()

        val apolloCall = apolloClient.query(query)

        return Rx2Apollo.from<UserRepositoriesQuery.Data>(apolloCall).map { mapResponseToList(it) }
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
    }

    private fun mapResponseToList(response: Response<UserRepositoriesQuery.Data>?): List<UserRepositoriesQuery.Node> {
        return response?.data()?.user()?.repositories()?.nodes() ?: emptyList()
    }
}