package com.jeongg.ieum.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ieum.R
import com.jeongg.ieum.presentation._common.IeumTextField
import com.jeongg.ieum.presentation._common.SelectedButton
import com.jeongg.ieum.presentation._common.UnSelectedButton
import com.jeongg.ieum.ui.theme.Dimens.NormalPadding

@Composable
fun LoginScreen(
    navController: NavController
){
    LazyColumn(
        modifier = Modifier.padding(NormalPadding),
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item { IconImage() }
        item { EmailField() }
        item { PasswordField() }
        item { LoginButton() }
        item { SignupButton() }
    }
}

@Composable
fun SignupButton() {
    UnSelectedButton(text = "회원가입")
}

@Composable
fun LoginButton() {
    SelectedButton(text = "로그인")
}

@Composable
fun PasswordField() {
    IeumTextField(placeholder = "비밀번호를 입력해주세요.")
    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun EmailField() {
    IeumTextField(placeholder = "이메일을 입력해주세요.")
}

@Composable
fun IconImage() {
    Image(
        painter = painterResource(id = R.drawable.ieum_icon),
        contentDescription = "ieum_icon",
        modifier = Modifier.size(145.dp).padding(bottom = 76.dp)
    )
}