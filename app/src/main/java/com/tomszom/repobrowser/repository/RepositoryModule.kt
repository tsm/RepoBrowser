package com.tomszom.repobrowser.repository

import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun bindRepositoryView(repositoryFragment: RepositoryFragment): RepositoryContract.View =
        repositoryFragment

    @Provides
    fun provideRepositoryPresenter(view: RepositoryContract.View): RepositoryContract.Presenter =
        RepositoryPresenter(view)


}