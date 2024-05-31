package com.jeongg.ieum.presentation.onboarding_interest

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ieum.presentation._common.LaunchedEffectEvent
import com.jeongg.ieum.presentation._common.OnboardingTheme
import com.jeongg.ieum.presentation._navigation.Screen
import com.jeongg.ieum.presentation.interest.component.InterestItemList

@Composable
fun OnboardingInterestScreen(
    navController: NavController,
    viewModel: OnboardingInterestViewModel = hiltViewModel()
) {
    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    OnboardingTheme (
        text = "관심있는 주제를\n3개 이상 선택해주세요.",
        buttonText = "완료하기",
        onClick = {
            navController.popBackStack()
            navController.navigate(Screen.ContentListScreen.route)
        },
    ) {
        viewModel.interestList.value.forEach { interest ->
            InterestItemList(
                mainCategory = interest.mainTopic,
                subCategory = interest.subTopic,
                onAddClick = { viewModel.createInterest(it) },
                onDeleteClick = { viewModel.deleteInterest(it) }
            )
        }
    }
}