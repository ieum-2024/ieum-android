package com.jeongg.ieum.presentation._common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.jeongg.ieum.presentation._util.IeumEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LaunchedEffectEvent(
    eventFlow: SharedFlow<IeumEvent>,
    onNavigate: () -> Unit = {},
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is IeumEvent.Navigate -> onNavigate()
                is IeumEvent.MakeToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is IeumEvent.Loading -> { }
            }
        }
    }
}