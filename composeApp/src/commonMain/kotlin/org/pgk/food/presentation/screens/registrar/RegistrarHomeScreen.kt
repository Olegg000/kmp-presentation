//package org.pgk.food.presentation.screens.registrar
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.koin.koinScreenModel // <-- ВОТ ПРАВИЛЬНЫЙ ИМПОРТ
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import org.jetbrains.compose.resources.painterResource
//import org.pgk.food.presentation.navigation.logout
//import org.pgk.food.presentation.components.QuickActionCard
//import org.pgk.food.presentation.screens.common.GroupsScreen
//
//import org.pgk.food.resources.Res
//import org.pgk.food.resources.*
//
//
//class RegistrarHomeScreen : Screen {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        // ИСПРАВЛЕНО: Используем koinScreenModel вместо getScreenModel
//        val viewModel = koinScreenModel<RegistrarViewModel>()
//
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Регистратор") },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer
//                    ),
//                    actions = {
//                        IconButton(onClick = { navigator.logout() }) {
//                            Icon(painterResource(Res.drawable.ic_logout), "Выход")
//                        }
//                    }
//                )
//            },
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    onClick = { navigator.push(CreateUserScreen()) },
//                    icon = { Icon(painterResource(Res.drawable.ic_user_add), null) },
//                    text = { Text("Добавить студента") }
//                )
//            }
//        ) { padding ->
//            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
//                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//                    // Quick Actions
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        QuickActionCard(
//                            title = "Группы",
//                            iconPainter = painterResource(Res.drawable.ic_users),
//                            onClick = { navigator.push(GroupsScreen()) },
//                            modifier = Modifier.weight(1f)
//                        )
//                        QuickActionCard(
//                            title = "Импорт CSV",
//                            iconPainter = painterResource(Res.drawable.ic_upload),
//                            onClick = { /* TODO */ },
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//
//                    Spacer(Modifier.height(24.dp))
//
//                    Text(
//                        "Недавно добавленные",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(Modifier.height(12.dp))
//
//                    Text(
//                        "Здесь будет список недавно добавленных пользователей",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//            }
//        }
//    }
//}