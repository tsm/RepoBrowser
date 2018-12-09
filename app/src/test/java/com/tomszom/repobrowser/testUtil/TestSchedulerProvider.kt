package com.tomszom.repobrowser.testUtil

import com.tomszom.repobrowser.core.util.SchedulerProvider
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider() : SchedulerProvider {

    private val testScheduler: TestScheduler = TestScheduler()

    override fun uiScheduler() = testScheduler
    override fun ioScheduler() = testScheduler
}