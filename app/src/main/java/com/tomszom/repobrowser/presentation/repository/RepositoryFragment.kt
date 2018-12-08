package com.tomszom.repobrowser.presentation.repository

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.UserRepositoriesQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.repository_fragment.*
import okhttp3.OkHttpClient


class RepositoryFragment : Fragment() {

    private var repositoriesDisposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repository_fragment, container, false)
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

    private fun loadRepositories(){
        val serverUrl = "https://api.github.com/graphql"

        val token = getString(R.string.github_oauth_token)

        val apolloClient = ApolloClient.builder()
            .serverUrl(serverUrl)
            .logger { priority, message, t, args -> Log.d("apolloLogger", message) }
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
            .subscribe(
                { response ->
                    showRepositories(mapResponseToList(response))
                },
                { e ->
                    e.printStackTrace()
                }
            )
    }

    private fun showRepositories(list: List<UserRepositoriesQuery.Node>) {
        val sb = StringBuilder()
        list.map { sb.append(it.name()).append("\n") }
        repositoryEmptyTV.text = sb.toString()
    }

    private fun mapResponseToList(response: Response<UserRepositoriesQuery.Data>?): List<UserRepositoriesQuery.Node> {
        return response?.data()?.user()?.repositories()?.nodes()?: emptyList()
    }
}
