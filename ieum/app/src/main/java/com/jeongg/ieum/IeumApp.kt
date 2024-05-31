package com.jeongg.ieum

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

val Context.dataStore by preferencesDataStore("ieum_data")

@HiltAndroidApp
class IeumApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}