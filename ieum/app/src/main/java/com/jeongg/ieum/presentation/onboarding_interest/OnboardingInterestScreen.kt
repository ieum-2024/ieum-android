package com.jeongg.ieum.presentation.onboarding_interest

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jeongg.ieum.presentation._common.OnboardingTheme
import com.jeongg.ieum.presentation.interest.component.InterestItemList

@Composable
fun OnboardingInterestScreen(
    navController: NavController
) {
    OnboardingTheme (
        text = "관심있는 주제를\n3개 이상 선택해주세요.",
        buttonText = "완료하기",
        onClick = { },
    ) {
        InterestItemList("취업/진로","대학진학,창업,자격증 취득,직업훈련".split(','))
        InterestItemList("전세/임대","전세자금 대출,정부 지원 정책,전세/임대전의 유의사항,전세 사기 리스크".split(','))
        InterestItemList("취업/진로",listOf("안녕하세요","반짝","웅"))
    }
}