package com.tomszom.repobrowser.core.di.module

import com.tomszom.repobrowser.core.di.scope.PerActivity
import com.tomszom.repobrowser.repository.RepositoryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun bindRepositoryActivity(): RepositoryActivity
}