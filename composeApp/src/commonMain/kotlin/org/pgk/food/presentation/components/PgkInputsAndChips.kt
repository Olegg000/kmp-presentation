package org.pgk.food.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ========================================================================
// CORE: Пружинящий движок (Улучшенный)
// Используем spring() для "живого" отскока
// ========================================================================

@Composable
fun PgkBouncySurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    color: Color = Color.Transparent,
    border: @Composable (() -> Unit)? = null, // Возможность добавить границу
    scaleDownFactor: Float = 0.92f, // Насколько сильно сжимать (0.92 = заметно)
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // ПРУЖИНА: Low stiffness делает анимацию мягкой, MediumBouncy добавляет отскок
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDownFactor else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "BounceAnimation"
    )

    val alpha = if (enabled) 1f else 0.5f

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clip(shape)
            .background(color)
            .then(
                if (enabled) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null, // Без ripple
                    onClick = onClick
                ) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (border != null) {
            border()
        }
        content()
    }
}

// ========================================================================
// КНОПКИ
// ========================================================================

/**
 * V1: PRIMARY BOLD
 * Солидная, высокая кнопка. Основной CTA.
 */
@Composable
fun PgkButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    PgkBouncySurface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp), // Высокая, удобно нажимать
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(18.dp) // Squircle
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * V1: SECONDARY TONAL
 * Светлый фон (PrimaryContainer), цветной текст.
 * Выглядит чище, чем Outlined (без рамки).
 */
@Composable
fun PgkButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    PgkBouncySurface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        // Прозрачный Primary (10-15%) или SurfaceVariant
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * DANGER BUTTON
 * Для удаления, выхода. Красный фон (Light) + Красный текст.
 */
@Composable
fun PgkButtonDanger(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    PgkBouncySurface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

/**
 * GHOST / TEXT BUTTON
 * Просто текст, но с анимацией нажатия.
 */
@Composable
fun PgkButtonGhost(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.height(40.dp).padding(horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

/**
 * FAB (FLOATING ACTION BUTTON)
 * Большая кнопка действия (скан QR, добавить).
 */
@Composable
fun PgkFab(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.size(64.dp), // Большая
        shape = RoundedCornerShape(22.dp), // Мягкий квадрат (Squircle)
        color = containerColor,
        scaleDownFactor = 0.85f // Сильный отскок
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(28.dp)
        )
    }
}

/**
 * SMALL ICON BUTTON
 * Для списков (edit, delete, settings).
 */
@Composable
fun PgkIconBtnSmall(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.size(42.dp),
        shape = RoundedCornerShape(14.dp),
        color = containerColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
    }
}

// ========================================================================
// ПОЛЯ ВВОДА (INPUTS)
// ========================================================================

/**
 * V2: MODERN FILLED INPUT
 * Стиль "Серый бокс". Без явной обводки, пока не в фокусе.
 * Очень мягкий и приятный.
 */
@Composable
fun PgkInput(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            // Убираем нижнюю полоску
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            // Делаем фон светло-серым (или tint от primary)
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            errorContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        leadingIcon = if (icon != null) {
            { Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
        } else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(16.dp)
    )
}

/**
 * PASSWORD INPUT (с глазом)
 */
@Composable
fun PgkInputPassword(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Пароль",
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    if (visible) Icons.Default.Close else Icons.Default.Face,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        singleLine = true
    )
}

/**
 * MULTILINE / DESCRIPTION
 */
@Composable
fun PgkInputMulti(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    minLines: Int = 3
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        minLines = minLines,
        maxLines = minLines + 2
    )
}

// ========================================================================
// ЧИПЫ И ПЕРЕКЛЮЧАТЕЛИ
// ========================================================================

/**
 * MODERN FILTER CHIP
 * Для фильтров (Сегодня / Неделя).
 */
@Composable
fun PgkChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val borderColor = if (selected) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)

    PgkBouncySurface(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(100.dp), // Полный круг (Pill)
        color = backgroundColor,
        border = {
            if (!selected) {
                Box(Modifier.fillMaxSize().border(1.dp, borderColor, RoundedCornerShape(100.dp)))
            }
        }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelLarge,
            color = contentColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

/**
 * STATUS CHIP
 * Маленькая плашка статуса (не кликабельная обычно).
 */
@Composable
fun PgkStatusBadge(
    text: String,
    type: PgkStatusType,
    modifier: Modifier = Modifier
) {
    val (bg, content) = when (type) {
        PgkStatusType.Success -> Color(0xFFE8F5E9) to Color(0xFF2E7D32) // Green
        PgkStatusType.Error -> Color(0xFFFFEBEE) to Color(0xFFC62828)   // Red
        PgkStatusType.Info -> Color(0xFFE3F2FD) to Color(0xFF1565C0)    // Blue
        PgkStatusType.Warning -> Color(0xFFFFF8E1) to Color(0xFFF9A825) // Yellow
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = content,
            fontSize = 10.sp
        )
    }
}

// Enum для статусов
enum class PgkStatusType { Success, Error, Info, Warning }

/**
 * ROLE TOGGLE (СЕГМЕНТ)
 * Переключатель ролей (Студент | Повар).
 */
@Composable
fun PgkRoleToggle(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { index, text ->
            val isSelected = index == selectedIndex
            // Внутренний вес, чтобы кнопки делили место поровну
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)
                    // Тень для выбранного элемента (опционально)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onOptionSelect(index) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

