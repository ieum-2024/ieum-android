package com.jeongg.ieum.presentation._common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.presentation._util.NoRippleInteractionSource
import com.jeongg.ieum.ui.theme.color_ebebeb
import com.jeongg.ieum.ui.theme.main_orange

@Composable
fun LongButton(
    text: String = "",
    onClick: () -> Unit = {},
    isSelected: Boolean = false,
){
    IeumButton(
        text = text,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = color(isSelected),
        textColor = textColor(isSelected),
        borderColor = textColor(isSelected)
    )
}

@Composable
fun ShortSelectableButton(
    text: String = "",
    selectedItem: MutableState<String>,
    onValueChange: () -> Unit
){
    val selected = selectedItem.value == text
    IeumButton(
        text = text,
        modifier = Modifier.wrapContentWidth(),
        color = color(selected),
        textColor = if (selected) Color.White else Color.Black,
        borderColor = if (selected) main_orange else color_ebebeb,
        shape = MaterialTheme.shapes.extraLarge,
        style = MaterialTheme.typography.bodyLarge,
        padding = 5.dp,
        onClick = {
            selectedItem.value = text
            onValueChange()
        }
    )
}

@Composable
fun ShortButton(
    text: String = "",
    onClick: () -> Unit,
    isSelected: Boolean = false,
){
    var selected by remember { mutableStateOf(isSelected) }
    IeumButton(
        text = text,
        modifier = Modifier.wrapContentWidth(),
        color = color(selected),
        textColor = if (selected) Color.White else Color.Black,
        borderColor = if (selected) main_orange else color_ebebeb,
        shape = MaterialTheme.shapes.extraLarge,
        style = MaterialTheme.typography.bodyLarge,
        padding = 5.dp,
        onClick = {
            onClick()
            selected = !selected
        }
    )
}

@Composable
private fun color(selected: Boolean) = if (selected) main_orange else Color.White

@Composable
private fun textColor(selected: Boolean) = if (selected) Color.White else main_orange

@Composable
fun IeumButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color,
    borderColor: Color,
    textColor: Color,
    text: String,
    shape: Shape = MaterialTheme.shapes.small,
    padding: Dp = 13.dp,
    style: TextStyle = MaterialTheme.typography.titleSmall
) {
    Button(
        modifier = modifier.wrapContentHeight(),
        onClick = onClick,
        shape = shape,
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = padding),
        interactionSource = NoRippleInteractionSource,
        border = if (color == main_orange) null else BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
        ),
    ){
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Center
        )
    }
}