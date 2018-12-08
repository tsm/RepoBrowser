package com.tomszom.repobrowser.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.apollographql.apollo.rx2.Rx2Apollo
import com.tomszom.repobrowser.BuildConfig
import com.tomszom.repobrowser.UserRepositoriesQuery
import com.tomszom.repobrowser.core.presentation.BasePresenter
import com.tomszom.repobrowser.type.CustomType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient

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
        val serverUrl = "https://api.github.com/graphql"

        val token = view.getToken()

        val uriCustomTypeAdapter = object : CustomTypeAdapter<String> {
            override fun decode(value: CustomTypeValue<*>): String {
                return value.value.toString()
            }

            override fun encode(value: String): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(value)
            }
        }

        val apolloClient = ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .authenticator { route, response ->
                        response.request().newBuilder()
                            .header("Authorization", "Token $token")
                            .build()
                    }
                    .build())
            .addCustomTypeAdapter(CustomType.URI, uriCustomTypeAdapter)
            .build()

        val query = UserRepositoriesQuery.builder().user_login(view.getGitUser()).build()

        val apolloCall = apolloClient.query(query)


        val repositoriesObservable = Rx2Apollo.from<UserRepositoriesQuery.Data>(apolloCall)

        repositoriesObservable
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

    private fun mapResponseToList(response: Response<UserRepositoriesQuery.Data>?): List<UserRepositoriesQuery.Node> {
        return response?.data()?.user()?.repositories()?.nodes() ?: emptyList()
    }
}