package com.tomszom.repobrowser.core.domain

import io.reactivex.Observable

abstract class UseCase<PARAM, RESULT> {

    abstract fun getObservable(param: PARAM): Observable<RESULT>


}