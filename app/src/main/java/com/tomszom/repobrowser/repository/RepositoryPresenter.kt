package com.tomszom.repobrowser.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.BuildConfig
import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.network.NetworkClientProvider
import com.tomszom.repobrowser.core.presentation.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoryPresenter : BasePresenter(), RepositoryContract.Presenter {

    lateinit var view: RepositoryContract.View

    override fun onResume() {
        super.onResume()
        loadRepositories()
    }

    override fun refreshAction() {
        loadRepositories()
    }

    private fun loadRepositories() {
        getRepositoryListObservable(view.getGitUser())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showProgress()
                view.hideEmpty()
            }
            .doOnTerminate { view.hideProgress() }
            .subscribe(
                { response ->
                    val list = mapResponseToList(response).reversed()
                    view.showRepositories(list)
                    if (list.isEmpty()) {
                        view.showEmpty()
                    }
                },
                { e ->
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace()
                    }
                    view.showError()
                }
            ).addWeakDisposable("REPOSITORY_LIST")
    }

    private fun getRepositoryListObservable(user: String): Observable<Response<UserRepositoriesQuery.Data>> {
        val apolloClient = NetworkClientProvider.provideApolloClient(view.getToken())

        val query = UserRepositoriesQuery.builder().user_login(user).build()

        val apolloCall = apolloClient.query(query)

        return Rx2Apollo.from<UserRepositoriesQuery.Data>(apolloCall)
    }

    private fun mapResponseToList(response: Response<UserRepositoriesQuery.Data>?): List<UserRepositoriesQuery.Node> {
        return response?.data()?.user()?.repositories()?.nodes() ?: emptyList()
    }
}