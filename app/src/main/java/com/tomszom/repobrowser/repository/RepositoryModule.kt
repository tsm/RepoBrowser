package com.tomszom.repobrowser.repository

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
        getRepositoriesUseCase: GetRepositoriesUseCase
    ): RepositoryContract.Presenter =
        RepositoryPresenter(view, getRepositoriesUseCase)


}