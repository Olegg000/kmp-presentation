//package org.pgk.food.presentation.screens.student
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.koin.koinScreenModel // Исправил на koinScreenModel
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import org.pgk.food.domain.model.MealType
//import org.pgk.food.domain.model.Ticket
//import org.pgk.food.presentation.navigation.logout
//
//class StudentHomeScreen : Screen {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        val viewModel = koinScreenModel<StudentHomeViewModel>()
//        val state by viewModel.state.collectAsState()
//
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Мои талоны") },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer
//                    ),
//                    actions = {
//                        IconButton(onClick = { navigator.push(ProfileScreen()) }) {
//                            Icon(Icons.Default.Person, "Профиль")
//                        }
//                        IconButton(onClick = {
//                            viewModel.logout()
//                            navigator.logout()
//                        }) {
//                            Icon(Icons.Default.ExitToApp, "Выход")
//                        }
//                    }
//                )
//            }
//        ) { padding ->
//            StudentHomeContent(
//                state = state,
//                onTicketClick = { ticket -> navigator.push(QRCodeScreen(ticket)) },
//                modifier = Modifier.padding(padding)
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun StudentHomeContent(
//    state: StudentHomeState,
//    onTicketClick: (Ticket) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Box(modifier = modifier.fillMaxSize()) {
//        when {
//            state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
//            state.error != null -> Text(state.error!!, Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.error)
//            state.tickets.isEmpty() -> {
//                Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(Icons.Default.Receipt, null, Modifier.size(64.dp))
//                    Text("Нет талонов")
//                }
//            }
//            else -> {
//                LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                    items(state.tickets) { ticket ->
//                        TicketCard(ticket = ticket, onClick = { onTicketClick(ticket) })
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TicketCard(ticket: Ticket, onClick: () -> Unit) {
//    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
//        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//            Box(Modifier.size(56.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
//                // Примечание: Icons.Default.Cookie и DinnerDining могут требовать extended библиотеку
//                // Если не компилируется, замени на Icons.Default.Star временно
//                Icon(Icons.Default.Star, null, tint = Color.White)
//            }
//            Spacer(Modifier.width(16.dp))
//            Column(Modifier.weight(1f)) {
//                Text(ticket.mealType.name, fontWeight = FontWeight.Bold)
//                Text(ticket.date)
//            }
//        }
//    }
//}