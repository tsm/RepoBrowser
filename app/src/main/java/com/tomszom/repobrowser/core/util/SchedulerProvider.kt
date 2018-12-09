package com.tomszom.repobrowser.core.util

import io.reactivex.Scheduler


interface SchedulerProvider {
    fun uiScheduler(): Scheduler
    fun ioScheduler(): Scheduler
}