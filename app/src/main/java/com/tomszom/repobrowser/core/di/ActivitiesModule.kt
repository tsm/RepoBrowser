package com.tomszom.repobrowser.core.di

import com.tomszom.repobrowser.core.di.scopes.PerActivity
import com.tomszom.repobrowser.repository.RepositoryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun bindRepositoryActivity(): RepositoryActivity
}