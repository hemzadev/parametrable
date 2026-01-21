
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.graphics.shapes.RoundedPolygon
import com.example.parametrable.R
import com.example.parametrable.parseColor
import com.example.parametrable.util.Config

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PerformanceCard(
    config: Config
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Today Performance",
                    style = MaterialTheme.typography.titleMedium
                )

                DrawMaterial3Shape(
                    size = 64.dp,
                    shape = MaterialShapes.Cookie12Sided,
                    color = parseColor(config.primaryColor)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MetricChip(
                    iconRes = R.drawable.outpatient_24px,
                    label = "Agents",
                    value = "25"
                )

                Spacer(modifier = Modifier.width(16.dp))

                MetricChip(
                    iconRes = R.drawable.partner_exchange_24px,
                    label = "Prospects",
                    value = "32"
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
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularIconContainer(
                iconRes = iconRes,
                contentDescription = label,
                size = 28.dp,
                iconColor = MaterialTheme.colorScheme.onPrimaryContainer
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
    containerColor: Color = MaterialTheme.colorScheme.onSecondaryFixedVariant,
    iconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = containerColor,
        tonalElevation = 1.dp
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
