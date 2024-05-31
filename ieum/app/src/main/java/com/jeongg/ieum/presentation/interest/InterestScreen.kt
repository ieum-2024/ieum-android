package com.jeongg.ieum.presentation.interest

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ieum.presentation._common.IeumThemeWithName
import com.jeongg.ieum.presentation._common.LaunchedEffectEvent
import com.jeongg.ieum.presentation.interest.component.InterestItemList
import com.jeongg.ieum.presentation.onboarding_interest.OnboardingInterestViewModel

@Composable
fun InterestScreen(
    navController: NavController,
    viewModel: OnboardingInterestViewModel = hiltViewModel()
) {
    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    IeumThemeWithName(title = "관심사 선택") {
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