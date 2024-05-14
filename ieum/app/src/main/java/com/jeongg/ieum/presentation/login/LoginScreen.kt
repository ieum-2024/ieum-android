package com.jeongg.ieum.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ieum.R
import com.jeongg.ieum.presentation._common.noRippleClickable
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.ui.theme.Dimens.NormalPadding
import com.jeongg.ieum.ui.theme.color_fee500

@Composable
fun LoginScreen(
    navController: NavController
){
    LazyColumn(
        modifier = Modifier.padding(NormalPadding),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item { AppIntroduction() }
        item { LoginImage() }
        item { KakaoLogin{ navController.navigate(Screen.OnboardingUserScreen.route) } }
    }
}

@Composable
private fun KakaoLogin(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .background(color_fee500, MaterialTheme.shapes.medium)
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 13.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_logo),
            contentDescription = "kakao login",
            modifier = Modifier.height(20.dp)
        )
        Text(
            text = "카카오 로그인",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun LoginImage() {
    Image(
        painter = painterResource(id = R.drawable.happy_boy),
        contentDescription = "happy_boy",
        modifier = Modifier.height(230.dp),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
private fun AppIntroduction() {
    Text(
        text = "자립준비청년과 시니어를",
        style = MaterialTheme.typography.displayMedium
    )
    Image(
        painter = painterResource(id = R.drawable.ieum_kor),
        contentDescription = "ieum in korean",
        modifier = Modifier.padding(top = 18.dp).height(55.dp)
    )
}
