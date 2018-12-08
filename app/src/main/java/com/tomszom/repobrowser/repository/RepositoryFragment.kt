package com.tomszom.repobrowser.repository

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.extension.gone
import com.tomszom.repobrowser.core.extension.visible
import com.tomszom.repobrowser.core.presentation.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.repository_fragment.*
import okhttp3.OkHttpClient


class RepositoryFragment : BaseFragment() {

    private val repositoryAdapter = RepositoryAdapter()

    override fun layoutId() = R.layout.repository_fragment

    private var repositoriesDisposable: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        loadRepositories()
    }

    override fun onPause() {
        super.onPause()
        if(repositoriesDisposable?.isDisposed == false){
            repositoriesDisposable?.dispose()
        }
    }

    private fun setupRecycler() {
        repositoryList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        repositoryList.adapter = repositoryAdapter
    }

    private fun loadRepositories(){
        val serverUrl = "https://api.github.com/graphql"

        val token = getString(R.string.github_oauth_token)

        val apolloClient = ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(OkHttpClient.Builder()
                .authenticator { route, response ->
                    response.request().newBuilder()
                        .header("Authorization", "Token $token")
                        .build()
                }
                .build())
            .build()

        val query = UserRepositoriesQuery.builder().user_login("tsm").build()

        val apolloCall = apolloClient.query(query)


        val repositoriesObservable = Rx2Apollo.from<UserRepositoriesQuery.Data>(apolloCall)

        if(repositoriesDisposable?.isDisposed == false){
            repositoriesDisposable?.dispose()
        }
        repositoriesDisposable = repositoriesObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showProgress()
                repositoryEmpty.gone()
            }
            .doOnTerminate { hideProgress() }
            .subscribe(
                { response ->
                    showRepositories(mapResponseToList(response).reversed())
                },
                { e ->
                    e.printStackTrace()
                    notifyWithAction(R.string.common_error, R.string.common_action_retry) { loadRepositories() }
                }
            )
    }

    private fun showRepositories(list: List<UserRepositoriesQuery.Node>) {
        repositoryAdapter.repositoryList = list
        if (list.isEmpty()) {
            repositoryEmpty.visible()
        }
    }

    private fun mapResponseToList(response: Response<UserRepositoriesQuery.Data>?): List<UserRepositoriesQuery.Node> {
        return response?.data()?.user()?.repositories()?.nodes()?: emptyList()
    }
}
