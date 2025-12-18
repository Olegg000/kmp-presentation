package org.pgk.food.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ========================================================================
// 1. TICKET (ТАЛОН)
// "Билет" с вырезами и пунктирной линией.
// Выглядит как реальный купон в Apple Wallet.
// ========================================================================

@Composable
fun PgkTicket(
    title: String,
    subtitle: String,
    status: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onClick: () -> Unit
) {
    // Цвета: Активный = PrimaryContainer, Неактивный = SurfaceVariant
    val containerColor = if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (isActive) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    val statusColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)

    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent, // Рисуем фон сами ниже
        scaleDownFactor = 0.96f
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Основной фон (делим на две части визуально, но здесь сплошной)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(containerColor)
            )

            // "Вырезы" (Circles) слева и справа
            // Это простой хак: накладываем круги цвета фона (background) поверх карточки
            val circleColor = MaterialTheme.colorScheme.background // Цвет фона экрана
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-12).dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(circleColor)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = 12.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(circleColor)
            )

            // Пунктирная линия
            Canvas(modifier = Modifier.fillMaxSize()) {
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                val y = size.height / 2 + 15.dp.toPx() // Линия чуть ниже центра или где удобно
                // Рисуем линию посередине между вырезами
                drawLine(
                    color = contentColor.copy(alpha = 0.2f),
                    start = Offset(24.dp.toPx(), size.height / 2),
                    end = Offset(size.width - 24.dp.toPx(), size.height / 2),
                    pathEffect = pathEffect,
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Контент
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Верх: Название и Статус
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = contentColor
                        )
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                    }
                    // Статус бейдж
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = status.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                }

                // Низ: ID или доп инфо
                Text(
                    text = "ID: T-${(1000..9999).random()}", // Фейковый ID для примера
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor.copy(alpha = 0.5f),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

// ========================================================================
// 2. QR PANELS (СТУДЕНТ И ПОВАР)
// ========================================================================

/**
 * STUDENT QR PANEL
 * Большая карточка с фокусом на QR-коде.
 */
@Composable
fun PgkQrPanelStudent(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(modifier = Modifier.height(16.dp))

        // QR Container
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White), // QR всегда на белом
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "QR Code",
                modifier = Modifier.size(160.dp),
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Обновится через 25с",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * CHEF SCANNER VIEW
 * Выглядит как видоискатель камеры.
 */
@Composable
fun PgkQrPanelChef(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Имитация камеры
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Наведите на QR-код",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Рамка сканера (углы)
        Box(
            modifier = Modifier
                .size(200.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
        )
    }
}

// ========================================================================
// 3. ROSTER ROW (СТРОКА ТАБЕЛЯ)
// ========================================================================

data class RosterMealUi(
    val name: String,
    val isSelected: Boolean
)

@Composable
fun PgkRosterRow(
    userName: String,
    userRole: String,
    meals: List<RosterMealUi>,
    onMealToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface) // Белый фон
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватарка / Иконка роли
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.take(1),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Имя и список переключателей
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = userRole,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Горизонтальный скролл чипов питания
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                meals.forEachIndexed { index, meal ->
                    PgkCompactMealToggle(
                        text = meal.name,
                        selected = meal.isSelected,
                        onClick = { onMealToggle(index) }
                    )
                }
            }
        }
    }
}

// Маленькая "таблетка" для ростера
@Composable
private fun PgkCompactMealToggle(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val content = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    PgkBouncySurface(
        onClick = onClick,
        modifier = Modifier.height(28.dp),
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.labelSmall,
            color = content,
            fontWeight = FontWeight.Medium
        )
    }
}

// ========================================================================
// 4. MENU CARDS (МЕНЮ)
// ========================================================================

/**
 * MENU ITEM (VIEW MODE)
 * Красивая карточка блюда.
 */
@Composable
fun PgkMenuCard(
    title: String,
    description: String,
    kcal: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        scaleDownFactor = 0.98f
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка блюда
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Калории
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    kcal,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * MENU ITEM (EDIT ROW)
 * Строка для быстрого редактирования списка.
 */
@Composable
fun PgkMenuEditRow(
    title: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        // Кнопки действий (используем IconBtnSmall из Part 1)
        PgkIconBtnSmall(
            icon = Icons.Default.Edit,
            onClick = onEdit,
            modifier = Modifier.size(36.dp),
            containerColor = Color.Transparent
        )
        PgkIconBtnSmall(
            icon = Icons.Default.Delete,
            onClick = onDelete,
            modifier = Modifier.size(36.dp),
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

