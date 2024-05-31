package com.jeongg.ieum.presentation.splash

import androidx.lifecycle.ViewModel
import com.jeongg.ieum.data._const.DataStoreKey
import com.jeongg.ieum.data.data_store.IeumDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: IeumDataStore
): ViewModel() {

    fun isUserLoggedIn(): Boolean{
        val token = dataStore.getData(DataStoreKey.ACCESS_TOKEN_KEY.name)
        return token.isNotBlank()
    }

}