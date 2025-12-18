//package org.pgk.food.presentation.screens.cook
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import org.jetbrains.compose.resources.painterResource
//import org.pgk.food.presentation.navigation.logout
//import org.pgk.food.presentation.components.QuickActionCard
//import org.pgk.food.resources.Res
//import org.pgk.food.resources.*
//
//class CookHomeScreen : Screen {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Столовая (Повар)") },
//                    actions = {
//                        IconButton(onClick = { navigator.logout() }) {
//                            Icon(painterResource(Res.drawable.ic_logout), "Выход")
//                        }
//                    }
//                )
//            }
//        ) { padding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding)
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Center
//            ) {
//                QuickActionCard(
//                    title = "СКАНИРОВАТЬ ТАЛОН",
//                    iconPainter = painterResource(Res.drawable.ic_qr_code),
//                    onClick = { navigator.push(QRScannerScreen()) },
//                    modifier = Modifier.fillMaxWidth().height(150.dp)
//                )
//
//                Spacer(Modifier.height(24.dp))
//
//                QuickActionCard(
//                    title = "История выдачи",
//                    iconPainter = painterResource(Res.drawable.ic_history), // Убедись, что скачал иконку clock -> ic_history
//                    onClick = { /* TODO: History Screen */ },
//                    modifier = Modifier.fillMaxWidth().height(100.dp)
//                )
//            }
//        }
//    }
//}