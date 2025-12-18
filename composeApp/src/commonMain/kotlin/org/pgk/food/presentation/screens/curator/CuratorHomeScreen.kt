//package org.pgk.food.presentation.screens.curator
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//
//// Импорты компонентов и навигации
//import org.pgk.food.presentation.components.QuickActionCard
//import org.pgk.food.presentation.navigation.logout
//import org.pgk.food.presentation.screens.common.RosterScreen // Убедись, что RosterScreen перенесен в common или импортирован верно
//
//// Твои ресурсы
//import org.jetbrains.compose.resources.painterResource
//import org.pgk.food.resources.Res
//import org.pgk.food.resources.ic_logout
//import org.pgk.food.resources.ic_calendar_edit
//import org.pgk.food.resources.ic_table_cells
//import org.pgk.food.resources.ic_chart_bar
//
//class CuratorHomeScreen : Screen {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Куратор") },
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
//                    onClick = { navigator.push(RosterScreen()) },
//                    icon = { Icon(painterResource(Res.drawable.ic_calendar_edit), null) },
//                    text = { Text("Заполнить табель") }
//                )
//            }
//        ) { padding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding)
//                    .padding(16.dp)
//            ) {
//                // Карточка группы (Static info пока что)
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
//                    )
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            "Моя группа",
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                        Spacer(Modifier.height(8.dp))
//                        Text(
//                            "ИС-22-1", // Тут потом привяжем данные из ViewModel
//                            style = MaterialTheme.typography.headlineMedium
//                        )
//                        Text(
//                            "24 студента",
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//                }
//
//                Spacer(Modifier.height(24.dp))
//
//                // Быстрые действия
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    QuickActionCard(
//                        title = "Табель",
//                        iconPainter = painterResource(Res.drawable.ic_table_cells),
//                        onClick = { navigator.push(RosterScreen()) },
//                        modifier = Modifier.weight(1f)
//                    )
//                    QuickActionCard(
//                        title = "Статистика",
//                        iconPainter = painterResource(Res.drawable.ic_chart_bar),
//                        onClick = { /* TODO: Экран статистики */ },
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//            }
//        }
//    }
//}