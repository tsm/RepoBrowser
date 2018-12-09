package com.tomszom.repobrowser.core.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module


@Module
abstract class AppModule {

    @Binds
    internal abstract fun bindContext(application: Application): Context

}