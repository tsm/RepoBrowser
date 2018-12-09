package com.tomszom.repobrowser.core.domain

import io.reactivex.Observable

abstract class UseCase<PARAM, RESULT> {

    abstract val tag: String
    abstract fun getObservable(param: PARAM): Observable<RESULT>


}