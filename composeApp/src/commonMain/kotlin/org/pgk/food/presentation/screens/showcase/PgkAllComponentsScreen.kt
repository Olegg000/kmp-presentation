package org.pgk.food.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ========================================================================
// 1. COLORS & SHADOWS (СТИЛЬ ПГК)
// ========================================================================

// Цвета на основе сайта ПГК, но адаптированные под мобайл
val PgkBlueDark = Color(0xFF355E8D) // Темно-синий (шапка сайта)
val PgkBlueLight = Color(0xFF5A89C2) // Светлее
val PgkAccentYellow = Color(0xFFFFC107) // Желтый для контраста (еда/уведомления)
val PgkBgColor = Color(0xFFF2F5F9) // Светло-серый фон (чтобы белые карты были видны)
val PgkTextBlack = Color(0xFF1A1E29)

/**
 * Утилита для мягкой "дорогой" тени (как в iOS/Ref 7)
 * Стандартный shadow() слишком грубый.
 */
fun Modifier.softShadow(
    color: Color = Color.Black.copy(alpha = 0.1f),
    borderRadius: Dp = 20.dp,
    blurRadius: Dp = 10.dp,
    offsetY: Dp = 4.dp,
    spread: Dp = 0.dp
) = this.drawBehind {
    // Симуляция размытия: рисуем несколько полупрозрачных слоев со смещением.
    // Это дешевый и рабочий способ сделать "Glow" эффект без нативного BlurMaskFilter.

    val shadowColor = color
    val steps = 3 // Количество слоев для мягкости (можно увеличить до 5)
    val stepAlpha = 1f / steps

    // Рисуем тень слоями, чтобы имитировать размытие краев
    for (i in 1..steps) {
        val currentBlur = (blurRadius.toPx() / steps) * i
        val currentSpread = spread.toPx() + (currentBlur / 2) // Немного расширяем
        val currentAlpha = shadowColor.alpha * stepAlpha

        drawRoundRect(
            color = shadowColor.copy(alpha = currentAlpha),
            topLeft = androidx.compose.ui.geometry.Offset(
                x = 0f - currentSpread,
                y = offsetY.toPx() - currentSpread + (currentBlur / 2) // Слегка сдвигаем слои вниз
            ),
            size = androidx.compose.ui.geometry.Size(
                width = size.width + (currentSpread * 2),
                height = size.height + (currentSpread * 2)
            ),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(borderRadius.toPx())
        )
    }
}

// ========================================================================
// 2. HEADER WITH CURVE (ШАПКА С ВОЛНОЙ)
// Как на референсе №2: Синий фон с дугой снизу
// ========================================================================

@Composable
fun PgkCurvedHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        // 1. Синий фон с кривой линией внизу
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp) // Высота синей шапки
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, size.height - 50) // Идем вниз, не доходя до конца
                // Рисуем дугу (Bezier curve)
                quadraticBezierTo(
                    size.width / 2, size.height + 50, // Контрольная точка (тянет вниз)
                    size.width, size.height - 50      // Конечная точка
                )
                lineTo(size.width, 0f)
                close()
            }

            // Градиент как на логотипе ПГК
            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(PgkBlueDark, PgkBlueLight)
                )
            )
        }

        // 2. Контент внутри шапки (Текст)
        Column(
            modifier = Modifier
                .padding(top = 60.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = subtitle,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                color = Color.White.copy(alpha = 0.8f)
            )
        }

        // 3. Сюда мы передадим карточки, которые будут "наезжать" на шапку
        Box(
            modifier = Modifier
                .padding(top = 160.dp) // Сдвиг вниз, чтобы наехать на синий фон
                .fillMaxWidth()
        ) {
            content()
        }
    }
}

// ========================================================================
// 3. SOFT INPUTS (ПУХЛЫЕ ПОЛЯ ВВОДА)
// Как на референсе №7: Белая плашка, тень, иконка внутри
// ========================================================================

@Composable
fun PgkSoftInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    // Контейнер поля
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp) // Высокое поле
            .softShadow(borderRadius = 20.dp, color = Color.Black.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PgkBlueLight,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Само поле (BasicTextField позволяет убрать все стандартные линии)
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(placeholder, color = Color.Gray.copy(alpha = 0.5f))
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = PgkTextBlack,
                    fontWeight = FontWeight.Medium
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ========================================================================
// 4. COLORFUL CARDS (ЦВЕТНЫЕ БЛОКИ)
// Как на референсе №1: Яркая заливка, белый текст
// ========================================================================

@Composable
fun PgkColorCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color = PgkBlueLight, // Можно менять на Желтый/Оранжевый
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(160.dp) // Высокая карточка
            .clip(RoundedCornerShape(28.dp)) // Сильное скругление
            .background(color)
            .clickable(onClick = onClick)
    ) {
        // Декор: полупрозрачный круг в углу (для красоты)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.1f),
                radius = 80.dp.toPx(),
                center = Offset(size.width, 0f)
            )
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Иконка в кружочке
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Текст
            Column {
                Text(
                    text = title,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ========================================================================
// SHOWCASE: HOME SCREEN (Как на Ref 2 + Ref 1)
// ========================================================================

@Composable
fun PgkRefStyleShowcase() {
    var searchText by remember { mutableStateOf("") }

    // Фон всего экрана (светло-серый, чтобы белое выделялось)
    Box(modifier = Modifier.fillMaxSize().background(PgkBgColor)) {

        // 1. Шапка с волной
        PgkCurvedHeader(
            title = "Привет, Студент!",
            subtitle = "Группа ИС-22 • ПГК"
        ) {
            // Контент, который "плавает" поверх шапки
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Поле поиска (Ref 7 style)
                PgkSoftInput(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = "Найти блюдо...",
                    icon = androidx.compose.material.icons.Icons.Default.Search
                )

                // Сетка цветных карточек (Ref 1 style)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Синяя карточка (Талоны)
                    PgkColorCard(
                        title = "Талоны",
                        subtitle = "Активных: 2",
                        icon = androidx.compose.material.icons.Icons.Default.AccountCircle,
                        color = PgkBlueLight,
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )

                    // Желтая карточка (Меню)
                    PgkColorCard(
                        title = "Меню",
                        subtitle = "На сегодня",
                        icon = androidx.compose.material.icons.Icons.Default.Menu,
                        color = Color(0xFFEAA92A), // Золотой/Оранжевый
                        modifier = Modifier.weight(1f),
                        onClick = {}
                    )
                }

                // Список "Сегодня" (Ref 8 style)
                Text(
                    "История операций",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PgkTextBlack)
                )

                // Карточка списка (Белая, с тенью)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .softShadow(borderRadius = 24.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PgkListItem(
                        title = "Обед (Столовая 1)",
                        time = "13:45",
                        price = "-1 Талон",
                        iconBg = Color(0xFFE3F2FD),
                        iconTint = PgkBlueDark
                    )
                    Divider(color = Color.Gray.copy(alpha = 0.1f))
                    PgkListItem(
                        title = "Покупка булочки",
                        time = "11:20",
                        price = "45 ₽",
                        iconBg = Color(0xFFFFF8E1),
                        iconTint = Color(0xFFFFA000)
                    )
                }

                // Отступ снизу
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // Кнопка по центру внизу (как ты просил, без сильного отступа)
        // Имитируем Ref 4 (Floating Button)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        ) {
            FloatingActionButton(
                onClick = {},
                containerColor = PgkTextBlack, // Черная кнопка как в Ref 1/4
                contentColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(androidx.compose.material.icons.Icons.Default.Build, null, modifier = Modifier.size(28.dp))
            }
        }
    }
}

// Хелпер для списка (как на Ref 8)
@Composable
fun PgkListItem(title: String, time: String, price: String, iconBg: Color, iconTint: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                androidx.compose.material.icons.Icons.Default.ShoppingCart,
                null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PgkTextBlack)
            Text(time, color = Color.Gray, fontSize = 14.sp)
        }
        Text(price, fontWeight = FontWeight.Bold, color = if(price.contains("Талон")) PgkBlueDark else PgkTextBlack)
    }
}