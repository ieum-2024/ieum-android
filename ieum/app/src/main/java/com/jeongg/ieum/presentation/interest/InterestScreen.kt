package com.jeongg.ieum.presentation.interest

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jeongg.ieum.presentation._common.IeumThemeWithName
import com.jeongg.ieum.presentation.interest.component.InterestItemList

@Composable
fun InterestScreen(
    navController: NavController
) {
    IeumThemeWithName("관심사 선택") {
        InterestItemList("취업/진로","대학진학,창업,자격증 취득,직업훈련".split(','))
        InterestItemList("전세/임대","전세자금 대출,정부 지원 정책,전세/임대전의 유의사항,전세 사기 리스크".split(','))
        InterestItemList("취업/진로",listOf("안녕하세요","반짝","웅"))
    }
}