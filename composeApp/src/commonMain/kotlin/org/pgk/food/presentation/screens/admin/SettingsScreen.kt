package org.pgk.food.presentation.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import org.pgk.food.resources.Res
import org.pgk.food.resources.*

class SettingsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Настройки") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(painterResource(Res.drawable.ic_arrow_left), "Назад")
                        }
                    }
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Text("Настройки системы", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }

                item { SettingItem("URL сервера", "https://food.pgk.apis.alspio.com", painterResource(Res.drawable.ic_cloud)) }
                item { SettingItem("Период валидности QR", "30 секунд", painterResource(Res.drawable.ic_clock)) }
                item { SettingItem("Автосинхронизация", "Включена", painterResource(Res.drawable.ic_sync)) }

                item { Spacer(Modifier.height(16.dp)); Text("О приложении", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }

                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Версия: 1.0.0")
                            Text("Сборка: Debug")
                            Text("Kotlin Multiplatform")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItem(title: String, value: String, icon: Painter) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null)
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}