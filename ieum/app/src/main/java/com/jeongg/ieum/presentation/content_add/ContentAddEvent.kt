package com.jeongg.ieum.presentation.content_add

import android.net.Uri
import java.io.File

sealed class ContentAddEvent {
    data class EnterTitle(val title: String): ContentAddEvent()
    data class EnterDescription(val description: String): ContentAddEvent()
    data class EnterInterest(val interestId: Long): ContentAddEvent()
    data class EnterImage(val uri: List<File>): ContentAddEvent()
    data object SaveContent: ContentAddEvent()
}