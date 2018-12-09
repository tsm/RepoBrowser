package com.tomszom.repobrowser.core.domain

import io.reactivex.Observable

interface UseCase<PARAM, RESULT> {
    fun getTag(): String
    fun getObservable(param: PARAM): Observable<RESULT>
}