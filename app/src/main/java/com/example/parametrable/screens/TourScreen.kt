package com.example.parametrable.screens

import PerformanceCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parametrable.features.Tour.TourCard
import com.example.parametrable.parseColor
import com.example.parametrable.ui.nav.CenterScreen
import com.example.parametrable.util.ActionType
import com.example.parametrable.util.Config

@Composable
fun TourScreen(
    config: Config,
    onActionClick: (ActionType) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            TourCard(
                cardConfig = config.homeScreen.performanceCard,
                appPrimary = parseColor(config.primaryColor)
            )
        }
    }
}