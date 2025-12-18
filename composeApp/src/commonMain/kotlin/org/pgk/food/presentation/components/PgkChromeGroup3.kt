package org.pgk.food.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ========================================================================
// 1. HISTORY & STATS (ИСТОРИЯ И СТАТИСТИКА)
// ========================================================================

/**
 * HISTORY ROW
 * Строка истории операций.
 */
@Composable
fun PgkHistoryRow(
    title: String,
    subtitle: String,
    time: String,
    modifier: Modifier = Modifier,
    isSuccess: Boolean = true,
    onClick: () -> Unit
) {
    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        scaleDownFactor = 0.98f
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка статуса
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isSuccess) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.errorContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isSuccess) Icons.Default.Check else Icons.Default.Add,
                    contentDescription = null,
                    tint = if (isSuccess) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * STATS CARD
 * Карточка со статистикой (большие цифры).
 */
@Composable
fun PgkStatsCard(
    title: String,
    value: String,
    subValue: String? = null,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    PgkBouncySurface(
        onClick = {}, // Статистика обычно не кликабельна, но эффект приятный
        enabled = false, // Отключаем клик, оставляем визуал
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (subValue != null) {
                Text(
                    text = subValue,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * FRAUD ROW (WARNING)
 * Красная/Оранжевая строка для подозрительных операций.
 */
@Composable
fun PgkFraudRow(
    title: String,
    reason: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        // Используем ErrorContainer с прозрачностью для мягкости
        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "!",
                    color = MaterialTheme.colorScheme.onError,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = reason,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ========================================================================
// 2. NAVIGATION (TOP & BOTTOM BARS)
// ========================================================================

/**
 * MODERN TOP BAR
 * Прозрачный или цветной заголовок.
 */
@Composable
fun PgkTopBar(
    title: String,
    modifier: Modifier = Modifier,
    startAction: (@Composable () -> Unit)? = null,
    endAction: (@Composable () -> Unit)? = null,
    centered: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 4.dp), // Отступы для кнопок
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Левая часть (Кнопка назад или пусто)
        Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
            if (startAction != null) startAction()
        }

        // Центр (Заголовок)
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = if (centered) Alignment.Center else Alignment.CenterStart
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Правая часть (Кнопка меню или пусто)
        Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
            if (endAction != null) endAction()
        }
    }
}

/**
 * FLOATING BOTTOM BAR (ISLAND STYLE)
 * Парящий островок навигации.
 */
data class PgkNavTab(
    val title: String,
    val icon: ImageVector,
    val isSelected: Boolean
)

@Composable
fun PgkBottomBar(
    tabs: List<PgkNavTab>,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Контейнер "Острова"
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp) // Отступы от краев экрана
            .height(64.dp),
        shape = RoundedCornerShape(32.dp), // Сильное скругление
        color = MaterialTheme.colorScheme.surfaceContainerHigh, // Чуть темнее обычного фона
        shadowElevation = 8.dp, // Хорошая тень, чтобы "парил"
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, tab ->
                val color = if (tab.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

                // Элемент навигации
                PgkBouncySurface(
                    onClick = { onTabSelected(index) },
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f), // Равномерное распределение
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Transparent,
                    scaleDownFactor = 0.8f // Сильный отклик при нажатии
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Анимируем иконку (можно добавить animateColorAsState)
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = color,
                            modifier = Modifier.size(24.dp)
                        )
                        if (tab.isSelected) {
                            // Маленькая точка под иконкой для активного таба
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ========================================================================
// 3. SCAN FEEDBACK (TOASTS / BANNERS)
// ========================================================================

/**
 * SCAN RESULT BANNER
 * Появляется снизу или сверху при сканировании.
 */
@Composable
fun PgkScanResultBanner(
    message: String,
    isSuccess: Boolean,
    modifier: Modifier = Modifier
) {
    val containerColor = if (isSuccess) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
    val contentColor = if (isSuccess) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
    val icon = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Close

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

