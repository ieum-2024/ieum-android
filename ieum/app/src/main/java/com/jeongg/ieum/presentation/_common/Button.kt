package com.jeongg.ieum.presentation._common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.presentation._util.NoRippleInteractionSource
import com.jeongg.ieum.ui.theme.main_orange

@Composable
fun SelectedButton(
    text: String = "",
    onClick: () -> Unit = {},
){
    IeumButton(
        onClick = onClick,
        color = main_orange,
        textColor = Color.White,
        text = text
    )
}


@Composable
fun UnSelectedButton(
    text: String = "",
    onClick: () -> Unit = {},
){
    IeumButton(
        onClick = onClick,
        color = Color.White,
        textColor = main_orange,
        text = text
    )
}

@Composable
fun IeumButton(
    onClick: () -> Unit,
    color: Color,
    textColor: Color,
    text: String
) {
    Button(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(vertical = 13.dp),
        interactionSource = NoRippleInteractionSource,
        border = if (color == main_orange) null else BorderStroke(1.dp, textColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
        ),
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}