package com.tomszom.repobrowser.core.di.module

import android.app.Application
import android.content.Context
import com.tomszom.repobrowser.core.di.scope.PerApplication
import com.tomszom.repobrowser.core.util.AppSchedulerProvider
import com.tomszom.repobrowser.core.util.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @PerApplication
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

}