package com.jeongg.ieum.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ieum.presentation._common.IeumTextField
import com.jeongg.ieum.presentation._common.IeumThemeWithName
import com.jeongg.ieum.presentation._common.LongButton
import com.jeongg.ieum.presentation._common.ShortButton


@Composable
fun SignupScreen(
    navController: NavController
) {
    IeumThemeWithName("회원가입") {
        NicknameField()
        EmailField()
        PasswordField()
        PasswordConfirmField()
        MentorSelect()
        ContinueButton()
    }

}

@Composable
private fun SignupField(
    text: String = "",
    placeholder: String = ""
) {
    Text(
        text = text,
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(bottom = 9.dp)
    )
    IeumTextField(placeholder = placeholder)
    Spacer(modifier = Modifier.height(21.dp))
}

@Composable
private fun NicknameField() {
    SignupField("닉네임", "3~10자 이내로 입력해주세요.")
}

@Composable
private fun EmailField() {
    SignupField("이메일", "이메일 형식을 지켜주세요.")
}

@Composable
private fun PasswordField() {
    SignupField("비밀번호", "숫자, 영어, 특수문자를 포함하여 주세요.")
}

@Composable
private fun PasswordConfirmField() {
    SignupField("비밀번호 확인", "비밀번호를 한 번 더 입력해 주세요.")
}

@Composable
private fun MentorSelect() {
    val selectedItem = remember { mutableStateOf("") }
    Text(
        text = "역할",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(bottom = 9.dp)
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ShortButton("멘토", selectedItem)
        ShortButton("멘티", selectedItem)
    }
}



@Composable
private fun ContinueButton() {
    Spacer(Modifier.height(50.dp))
    LongButton(text = "계속하기", isSelected = true)
    Spacer(Modifier.height(10.dp))
}
