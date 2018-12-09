package com.tomszom.repobrowser.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.domain.UseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class GetUserRepositoriesUseCase @Inject constructor(private val apolloClient: ApolloClient) :
    UseCase<String, List<UserRepositoriesQuery.Node>>() {

    companion object {
        const val TAG = "GetUserRepositoriesUseCase"
    }

    override fun getObservable(param: String): Observable<List<UserRepositoriesQuery.Node>> {

        val query = UserRepositoriesQuery.builder().user_login(param).build()

        val apolloCall = apolloClient.query(query)

        return Rx2Apollo.from<UserRepositoriesQuery.Data>(apolloCall).map { mapResponseToList(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun mapResponseToList(response: Response<UserRepositoriesQuery.Data>?): List<UserRepositoriesQuery.Node> {
        return response?.data()?.user()?.repositories()?.nodes() ?: emptyList()
    }
}