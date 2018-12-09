package com.tomszom.repobrowser.repository

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun bindRepositoryView(repositoryFragment: RepositoryFragment): RepositoryContract.View =
        repositoryFragment

    @Provides
    fun provideRepositoryPresenter(
        view: RepositoryContract.View,
        apolloClient: ApolloClient
    ): RepositoryContract.Presenter =
        RepositoryPresenter(view, apolloClient)


}