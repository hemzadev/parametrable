import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.graphics.shapes.RoundedPolygon
import com.example.parametrable.R
import com.example.parametrable.config_wl.PerformanceCardConfig
import com.example.parametrable.config_wl.PerformanceCardStyling

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PerformanceCard(
    cardConfig: PerformanceCardConfig,
    appPrimary: Color = MaterialTheme.colorScheme.primary
) {
    when (cardConfig.styling) {
        PerformanceCardStyling.STYLE_1 -> PerformanceCardStyle1(cardConfig, appPrimary)
        PerformanceCardStyling.STYLE_2 -> PerformanceCardStyle2(cardConfig, appPrimary)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun PerformanceCardStyle1(
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DrawMaterial3Shape(
                    size = 48.dp,
                    shape = MaterialShapes.Cookie12Sided,
                    color = appPrimary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.performance_today_title),
                    style = MaterialTheme.typography.headlineLargeEmphasized,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

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


@Composable
private fun PerformanceCardStyle2(
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Performance",
                style = MaterialTheme.typography.titleLarge,
                color = appPrimary
            )

            if (cardConfig.progressBarEnabled) {
                ProgressMetric(label = "Visits", current = 12, target = 50)
            }

            if (cardConfig.indicatorsEnabled) {
                Text(
                    "Indicators enabled",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DrawMaterial3Shape(
    size: Dp,
    shape: RoundedPolygon,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape.toShape())
            .background(color)
    )
}

@Composable
fun MetricChip(
    iconRes: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.onPrimary,
        border = BorderStroke(0.5.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularIconContainer(
                iconRes = iconRes,
                contentDescription = label,
                size = 28.dp,
                iconColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "$label: $value",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
fun CircularIconContainer(
    iconRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = containerColor,
        tonalElevation = 1.dp,
        border = BorderStroke(0.5.dp, Color.LightGray)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription,
                tint = iconColor,
                modifier = Modifier.size((size.value * 0.62f).dp) // scales with container
            )
        }
    }
}


@Composable
fun ProgressMetric(
    label: String,
    current: Int,
    target: Int,
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Filled.TrendingUp
) {
    val rawProgress = (current.toFloat() / target.coerceAtLeast(1)).coerceIn(0f, 1f)
    val progress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(durationMillis = 650),
        label = "progressAnim"
    )
    val percent = (rawProgress * 100).toInt().coerceIn(0, 100)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // small icon badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    tonalElevation = 1.dp
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(8.dp).size(18.dp)
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(
                            R.string.performance_progress_format,
                            current,
                            target
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // percent pill
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = stringResource(
                            R.string.performance_percent_format,
                            percent
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                }
            }

            Spacer(Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(MaterialTheme.shapes.large),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}