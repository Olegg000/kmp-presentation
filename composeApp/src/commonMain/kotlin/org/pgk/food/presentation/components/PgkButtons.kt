//package org.pgk.food.presentation.components
//
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.interaction.collectIsPressedAsState
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import org.jetbrains.compose.ui.tooling.preview.Preview
//
//// ========================================================================
//// 1. ДВИЖОК: "Пружинящая" поверхность (Bouncy Surface)
//// ========================================================================
//
//@Composable
//fun PgkBouncySurface(
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    shape: Shape = RoundedCornerShape(20.dp),
//    color: Color = MaterialTheme.colorScheme.primary,
//    content: @Composable BoxScope.() -> Unit
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//    val isPressed by interactionSource.collectIsPressedAsState()
//
//    // Пружина: при нажатии уменьшается до 0.95, при отпускании возвращается
//    val scale by animateFloatAsState(
//        targetValue = if (isPressed) 0.96f else 1f,
//        animationSpec = tween(durationMillis = 100),
//        label = "ButtonScale"
//    )
//
//    val alpha = if (enabled) 1f else 0.5f
//
//    Box(
//        modifier = modifier
//            .scale(scale)
//            .graphicsLayer(alpha = alpha)
//            .clip(shape)
//            .background(color)
//            .clickable(
//                interactionSource = interactionSource,
//                indication = null, // Отключаем стандартную волну (Ripple)
//                enabled = enabled,
//                onClick = onClick
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        content()
//    }
//}
//
//// ========================================================================
//// 2. НОВЫЕ КНОПКИ (PREMIUM STYLE)
//// ========================================================================
//
//@Composable
//fun PgkButtonPrimary(
//    text: String,
//    modifier: Modifier = Modifier,
//    icon: ImageVector? = null,
//    enabled: Boolean = true,
//    onClick: () -> Unit
//) {
//    PgkBouncySurface(
//        onClick = onClick,
//        enabled = enabled,
//        modifier = modifier
//            .fillMaxWidth()
//            .height(56.dp), // Солидная высота
//        color = MaterialTheme.colorScheme.primary,
//        shape = RoundedCornerShape(20.dp) // Мягкий квадрат
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            if (icon != null) {
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimary,
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//            }
//            Text(
//                text = text,
//                style = MaterialTheme.typography.titleMedium.copy(
//                    fontSize = 17.sp,
//                    letterSpacing = 0.sp
//                ),
//                color = MaterialTheme.colorScheme.onPrimary,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}
//
//@Composable
//fun PgkButtonSecondary(
//    text: String,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    // Светлый фон (Tonal) вместо обводки
//    containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
//    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
//    onClick: () -> Unit
//) {
//    PgkBouncySurface(
//        onClick = onClick,
//        enabled = enabled,
//        modifier = modifier
//            .fillMaxWidth()
//            .height(56.dp),
//        color = containerColor,
//        shape = RoundedCornerShape(20.dp)
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
//            color = contentColor,
//            fontWeight = FontWeight.SemiBold
//        )
//    }
//}
//
//@Composable
//fun PgkButtonGhost(
//    text: String,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    color: Color = MaterialTheme.colorScheme.primary,
//    onClick: () -> Unit
//) {
//    PgkBouncySurface(
//        onClick = onClick,
//        enabled = enabled,
//        modifier = modifier.height(44.dp).padding(horizontal = 16.dp),
//        color = Color.Transparent, // Прозрачный
//        shape = RoundedCornerShape(12.dp)
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.bodyLarge,
//            color = color,
//            fontWeight = FontWeight.Medium
//        )
//    }
//}
//
//@Composable
//fun PgkIconButton(
//    icon: ImageVector,
//    modifier: Modifier = Modifier,
//    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
//    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
//    shape: Shape = RoundedCornerShape(16.dp), // Squircle
//    onClick: () -> Unit
//) {
//    PgkBouncySurface(
//        onClick = onClick,
//        modifier = modifier.size(52.dp),
//        color = backgroundColor,
//        shape = shape
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = contentColor,
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}
//
//// ========================================================================
//// 3. ШОУКЕЙС (ГАЛЕРЕЯ)
//// ========================================================================
//
//@Preview()
//@Composable
//fun PgkNewButtonsShowcase() {
//    Surface(
//        color = MaterialTheme.colorScheme.background,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(24.dp)
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.spacedBy(32.dp)
//        ) {
//            // Заголовок
//            Column {
//                Text(
//                    "PGK Design System 2.0",
//                    style = MaterialTheme.typography.headlineMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.primary
//                )
//                Text(
//                    "Premium Bouncy Buttons",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//
//            // СЕКЦИЯ 1: Главные действия
//            // ------------------------------------------------
//            SectionHeader("Main Actions (CTA)")
//
//            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                PgkButtonPrimary(text = "Оформить заказ") {}
//
//                PgkButtonPrimary(
//                    text = "Добавить в корзину",
//                    icon = Icons.Default.ShoppingCart
//                ) {}
//
//                PgkButtonSecondary(text = "Продолжить выбор") {}
//            }
//
//            // СЕКЦИЯ 2: Горизонтальные действия
//            // ------------------------------------------------
//            SectionHeader("Row Layout")
//            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                PgkButtonSecondary(
//                    text = "Отмена",
//                    modifier = Modifier.weight(1f),
//                    containerColor = MaterialTheme.colorScheme.surfaceVariant
//                ) {}
//                PgkButtonPrimary(
//                    text = "Сохранить",
//                    modifier = Modifier.weight(1f)
//                ) {}
//            }
//
//            // СЕКЦИЯ 3: Иконки (Squircle)
//            // ------------------------------------------------
//            SectionHeader("Icon Buttons (Squircles)")
//            Text(
//                "Нажми на них — они приятно пружинят!",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                PgkIconButton(icon = Icons.Default.ArrowBack) {}
//
//                PgkIconButton(
//                    icon = Icons.Default.Settings,
//                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                ) {}
//
//                PgkIconButton(
//                    icon = Icons.Default.Favorite,
//                    backgroundColor = Color(0xFFFFEBEE), // Light Red tint
//                    contentColor = Color(0xFFD32F2F)
//                ) {}
//
//                // Круглый вариант для сравнения
//                PgkIconButton(
//                    icon = Icons.Default.Add,
//                    shape = CircleShape,
//                    backgroundColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
//                ) {}
//            }
//
//            // СЕКЦИЯ 4: Реальный пример (Карточка)
//            // ------------------------------------------------
//            SectionHeader("Real World Example")
//
//            Card(
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.surface
//                ),
//                elevation = CardDefaults.cardElevation(4.dp),
//                shape = RoundedCornerShape(24.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(modifier = Modifier.padding(20.dp)) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Column {
//                            Text(
//                                "Авокадо Тост",
//                                style = MaterialTheme.typography.titleLarge,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Text(
//                                "350 ₽",
//                                style = MaterialTheme.typography.titleMedium,
//                                color = MaterialTheme.colorScheme.primary
//                            )
//                        }
//                        // Маленькая кнопка лайка
//                        var liked by remember { mutableStateOf(false) }
//                        PgkBouncySurface(
//                            onClick = { liked = !liked },
//                            modifier = Modifier.size(44.dp),
//                            shape = CircleShape,
//                            color = if(liked) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.surfaceVariant
//                        ) {
//                            Icon(
//                                if(liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                                null,
//                                tint = if(liked) Color(0xFFE53935) else Color.Gray,
//                                modifier = Modifier.size(24.dp)
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    // Большая кнопка купить
//                    PgkButtonPrimary(text = "В корзину") {}
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    // Кнопка подробнее
//                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                        PgkButtonGhost(text = "Посмотреть состав") {}
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(48.dp))
//        }
//    }
//}
//
//@Composable
//fun SectionHeader(text: String) {
//    Text(
//        text = text.uppercase(),
//        style = MaterialTheme.typography.labelLarge,
//        fontWeight = FontWeight.Bold,
//        color = MaterialTheme.colorScheme.outline,
//        modifier = Modifier.padding(top = 8.dp)
//    )
//}