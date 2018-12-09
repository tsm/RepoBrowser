package com.tomszom.repobrowser.core.di

import com.tomszom.repobrowser.repository.RepositoryFragment
import com.tomszom.repobrowser.repository.RepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = [RepositoryModule::class])
    abstract fun provideRepositoryFragment(): RepositoryFragment
}