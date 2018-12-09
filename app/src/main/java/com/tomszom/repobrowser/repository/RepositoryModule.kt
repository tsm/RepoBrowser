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
        getUserRepositoriesUseCase: GetUserRepositoriesUseCase
    ): RepositoryContract.Presenter =
        RepositoryPresenter(view, getUserRepositoriesUseCase)


}