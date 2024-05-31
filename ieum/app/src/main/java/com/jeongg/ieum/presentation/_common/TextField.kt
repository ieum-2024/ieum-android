package com.jeongg.ieum.presentation._common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeongg.ieum.presentation._util.NoRippleInteractionSource
import com.jeongg.ieum.ui.theme.color_808080
import com.jeongg.ieum.ui.theme.color_C2C2C2
import com.jeongg.ieum.ui.theme.color_ebebeb
import com.jeongg.ieum.ui.theme.main_orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IeumTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    minHeight: Dp = 44.dp,
    shape: Shape = MaterialTheme.shapes.small,
) {
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = minHeight)
            .focusRequester(focusRequester),
        textStyle = typography.bodyMedium,
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = it,
            enabled = true,
            singleLine = false,
            visualTransformation = VisualTransformation.None,
            interactionSource = NoRippleInteractionSource,
            placeholder = { TextFieldPlaceHolder(placeholder) },
            colors = OutlinedTextFieldDefaults.colors(),
            contentPadding = PaddingValues(10.dp),
            container = { TextFieldOutlineBorder(shape) },
        )
    }
}

@Composable
fun IeumBasicTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    text: String = "",
    placeholder: String = "",
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall
) {

    Box(
        modifier = modifier.padding(4.dp, 16.dp, 0.dp, 16.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = Modifier.fillMaxWidth()
        )
        if (text.isEmpty() && placeholder.isNotEmpty()) {
            Text(text = placeholder, style = textStyle, color = color_C2C2C2)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldOutlineBorder(shape: Shape) {
    OutlinedTextFieldDefaults.ContainerBox(
        enabled = true,
        isError = false,
        interactionSource = NoRippleInteractionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            containerColor = Color.White,
            focusedPlaceholderColor = color_ebebeb,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = color_ebebeb,
            cursorColor = main_orange
        ),
        shape = shape
    )
}

@Composable
private fun TextFieldPlaceHolder(placeholder: String) {
    Text(
        text = placeholder,
        style = typography.bodyMedium,
        color = color_808080,
    )
}
