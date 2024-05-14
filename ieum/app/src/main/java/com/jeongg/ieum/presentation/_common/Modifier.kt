package com.jeongg.ieum.presentation._common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import com.jeongg.ieum.presentation._util.NoRippleInteractionSource

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}
fun Modifier.noRippleClickable(onClick: () -> Unit = {}): Modifier {
    return this.clickable(
        interactionSource = NoRippleInteractionSource,
        indication = null,
        onClick = onClick
    )
}