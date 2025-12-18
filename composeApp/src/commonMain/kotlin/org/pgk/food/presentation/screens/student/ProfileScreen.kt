//package org.pgk.food.presentation.screens.student
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//
//class ProfileScreen : Screen {
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Профиль") },
//                    navigationIcon = {
//                        IconButton(onClick = { navigator.pop() }) {
//                            Icon(Icons.Default.ArrowBack, "Назад")
//                        }
//                    }
//                )
//            }
//        ) { padding ->
//            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
//                Text("Профиль студента (TODO)")
//            }
//        }
//    }
//}