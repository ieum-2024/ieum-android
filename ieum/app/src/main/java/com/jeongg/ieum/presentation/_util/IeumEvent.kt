package com.jeongg.ieum.presentation._util

sealed class IeumEvent {
    data object Loading: IeumEvent()
    data object Navigate: IeumEvent()
    data class MakeToast(val message: String): IeumEvent()
}