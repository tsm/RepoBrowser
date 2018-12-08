package com.tomszom.repobrowser.core.presentation

interface BaseContract {

    interface View {
        fun getLayoutId(): Int
        fun getViewId(): String
    }

    interface Presenter {
        fun onResume()
        fun onStop()
        fun onDestroyView()
    }

}