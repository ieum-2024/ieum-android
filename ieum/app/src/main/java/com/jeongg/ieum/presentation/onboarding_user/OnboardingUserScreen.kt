package com.jeongg.ieum.presentation.onboarding_user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ieum.R
import com.jeongg.ieum.data._const.Role
import com.jeongg.ieum.presentation._common.IeumTextField
import com.jeongg.ieum.presentation._common.LaunchedEffectEvent
import com.jeongg.ieum.presentation._common.OnboardingTheme
import com.jeongg.ieum.presentation._common.ShortSelectableButton
import com.jeongg.ieum.presentation._common.addFocusCleaner
import com.jeongg.ieum.presentation._navigation.Screen

@Composable
fun OnboardingUserScreen(
    navController: NavController,
    viewModel: OnboardingUserViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    LaunchedEffectEvent(
        eventFlow = viewModel.eventFlow,
        onNavigate = {
            navController.popBackStack()
            navController.navigate(Screen.OnboardingInterestScreen.route)
        }
    )
    OnboardingTheme (
        modifier = Modifier.addFocusCleaner(focusManager),
        text = "서비스 이용을 위한\n기본 정보를 입력해주세요.",
        buttonText = "계속하기",
        onClick = { viewModel.onEvent(OnboardingUserEvent.SaveUser) },
    ) {
        OnboardingImage()
        NicknameField(
            nickname = viewModel.userInfo.value.nickname,
            onValueChange = { viewModel.onEvent(OnboardingUserEvent.EnterNickname(it)) }
        )
        SelectRole { viewModel.onEvent(OnboardingUserEvent.EnterRole(it)) }
    }

}

@Composable
private fun OnboardingImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.happy_girl),
            contentDescription = "happy_boy",
            modifier = Modifier
                .height(230.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun SelectRole(
    onValueChange: (Role) -> Unit
) {
    val selectedItem = remember { mutableStateOf("") }
    Row(
        modifier = Modifier.padding(bottom = 60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "역할",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.width(70.dp)
        )
        Role.entries.forEach {role ->
            ShortSelectableButton(role.kor, selectedItem) { onValueChange(role) }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
private fun NicknameField(
    nickname: String,
    onValueChange: (String) -> Unit,

) {
    Row(
        modifier = Modifier.padding(bottom = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "닉네임",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.width(70.dp)
        )
        IeumTextField(
            text = nickname,
            placeholder = "3~10자 이내로 입력해주세요.",
            onValueChange = onValueChange,
        )
    }
}
