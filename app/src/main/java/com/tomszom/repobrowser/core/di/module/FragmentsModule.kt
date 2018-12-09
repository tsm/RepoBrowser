package com.tomszom.repobrowser.core.di.module

import com.tomszom.repobrowser.owner.OwnerFragment
import com.tomszom.repobrowser.owner.OwnerModule
import com.tomszom.repobrowser.repository.RepositoryFragment
import com.tomszom.repobrowser.repository.RepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = [OwnerModule::class])
    abstract fun provideOwnerFragment(): OwnerFragment

    @ContributesAndroidInjector(modules = [RepositoryModule::class])
    abstract fun provideRepositoryFragment(): RepositoryFragment
}