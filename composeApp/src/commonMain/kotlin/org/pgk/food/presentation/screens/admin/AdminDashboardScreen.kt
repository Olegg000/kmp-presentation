package org.pgk.food.presentation.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

// Импорты навигации и экранов
import org.pgk.food.presentation.navigation.logout
import org.pgk.food.presentation.screens.registrar.CreateUserScreen
import org.pgk.food.presentation.screens.registrar.GroupsScreen
import org.pgk.food.presentation.screens.common.RosterScreen
// import org.pgk.food.presentation.screens.cook.QRScannerScreen // Раскомментируй, когда создашь файл

// Ресурсы
import org.jetbrains.compose.resources.painterResource
import org.pgk.food.resources.Res
import org.pgk.food.resources.* // Импортирует все иконки

class AdminDashboardScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<AdminViewModel>()
        val state by viewModel.state.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Панель администратора") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    actions = {
                        IconButton(onClick = { navigator.push(ReportsScreen()) }) {
                            Icon(painterResource(Res.drawable.ic_document), "Отчёты")
                        }
                        IconButton(onClick = {
                            viewModel.logout()
                            navigator.logout()
                        }) {
                            Icon(painterResource(Res.drawable.ic_logout), "Выход")
                        }
                    }
                )
            }
        ) { padding ->
            AdminDashboardContent(
                state = state,
                onActionClick = { action ->
                    when (action) {
                        AdminAction.CREATE_USER -> navigator.push(CreateUserScreen())
                        AdminAction.MANAGE_GROUPS -> navigator.push(GroupsScreen())
                        AdminAction.SCAN_QR -> { /* navigator.push(QRScannerScreen()) */ }
                        AdminAction.MANAGE_ROSTER -> navigator.push(RosterScreen())
                        AdminAction.VIEW_REPORTS -> navigator.push(ReportsScreen())
                        AdminAction.SETTINGS -> navigator.push(SettingsScreen())
                    }
                },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun AdminDashboardContent(
    state: AdminDashboardState,
    onActionClick: (AdminAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Statistics
        item { Text("Статистика", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Всего студентов",
                    value = state.totalStudents.toString(),
                    icon = painterResource(Res.drawable.ic_users), // Используем users
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Активных групп",
                    value = state.totalGroups.toString(),
                    icon = painterResource(Res.drawable.ic_users),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Выдано сегодня",
                    value = state.mealsToday.toString(),
                    icon = painterResource(Res.drawable.ic_check_circle), // Или ic_ticket если скачал
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "За неделю",
                    value = state.mealsWeek.toString(),
                    icon = painterResource(Res.drawable.ic_calendar_edit),
                    color = MaterialTheme.colorScheme.errorContainer,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Quick Actions
        item {
            Spacer(Modifier.height(8.dp))
            Text("Быстрые действия", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().height(400.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(AdminAction.values()) { action ->
                    AdminActionCard(action = action, onClick = { onActionClick(action) })
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: Painter,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = color)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminActionCard(action: AdminAction, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(action.iconRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(action.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        }
    }
}

// Переделанный Enum для работы с ресурсами
enum class AdminAction(
    val title: String,
    val iconRes: org.jetbrains.compose.resources.DrawableResource
) {
    CREATE_USER("Добавить пользователя", Res.drawable.ic_user_add),
    MANAGE_GROUPS("Управление группами", Res.drawable.ic_users),
    SCAN_QR("Сканировать QR", Res.drawable.ic_qr_code),
    MANAGE_ROSTER("Табель питания", Res.drawable.ic_calendar_edit),
    VIEW_REPORTS("Отчёты", Res.drawable.ic_document),
    SETTINGS("Настройки", Res.drawable.ic_settings)
}