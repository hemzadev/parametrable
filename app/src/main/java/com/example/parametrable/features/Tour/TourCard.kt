package com.example.parametrable.features.Tour

import DrawMaterial3Shape
import MetricChip
import ProgressMetric
import android.view.MenuInflater
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.parametrable.R
import com.example.parametrable.config_wl.PerformanceCardConfig


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TourCard(
    cardConfig: PerformanceCardConfig,
    appPrimary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(0.5.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical =  16.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(MaterialShapes.Cookie12Sided.toShape())
                        .border(1.5.dp, MaterialTheme.colorScheme.outline, MaterialShapes.Cookie12Sided.toShape())
                        .clickable { }
                ) {
                    AsyncImage(
                        model = "file:///android_asset/avatarSales.jpg",
                        contentDescription = "user_profile_image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column(

                ) {

                    Text(
                        text = "Hamza Ben azza",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Marchant: BITS SOLUTIONS",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

            }

            Column() {

            }

            if (cardConfig.progressBarEnabled) {
                ProgressMetric(
                    label = stringResource(R.string.performance_visits_label),
                    current = 12,
                    target = 50
                )
            }

            if (cardConfig.indicatorsEnabled) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MetricChip(
                        iconRes = R.drawable.meeting_room_24px,
                        label = stringResource(R.string.performance_agents_label),
                        value = "25",
                        modifier = Modifier.weight(1f)
                    )

                    MetricChip(
                        iconRes = R.drawable.group_24px,
                        label = stringResource(R.string.performance_prospects_label),
                        value = "32",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}