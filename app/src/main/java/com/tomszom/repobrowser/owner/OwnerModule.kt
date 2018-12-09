package com.tomszom.repobrowser.owner

import dagger.Module
import dagger.Provides

@Module
class OwnerModule {

    @Provides
    fun bindOwnerView(ownerFragment: OwnerFragment): OwnerContract.View =
        ownerFragment

    @Provides
    fun provideOwnerPresenter(
        view: OwnerContract.View,
        getOwnersUseCase: GetOwnersUseCase
    ): OwnerContract.Presenter =
        OwnerPresenter(view, getOwnersUseCase)


}