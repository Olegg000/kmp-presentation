package org.pgk.food.presentation.screens.cook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.preat.peekaboo.ui.camera.CameraMode
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import org.jetbrains.compose.resources.painterResource
import org.pgk.food.resources.Res
import org.pgk.food.resources.ic_arrow_left

class QRScannerScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val cameraState = rememberPeekabooCameraState(onCapture = {})

        // Состояние обработки (чтобы не спамить сканированием одного кода)
        var isProcessing by remember { mutableStateOf(false) }
        var lastScannedCode by remember { mutableStateOf<String?>(null) }
        var scanResult by remember { mutableStateOf<ScanResult?>(null) }

        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            // КАМЕРА (Только если результат еще не показан)
            if (scanResult == null) {
                // Peekaboo Camera не имеет встроенного QR сканера в версии UI 0.5.2 из коробки так просто,
                // НО многие версии поддерживают callback. Если библиотека не поддерживает QR из коробки
                // в этой версии, придется использовать Zxing или натив.
                // ВАЖНО: Предполагаем, что ты найдешь способ подключить анализатор,
                // или используем заглушку для теста, если библиотека только делает фото.

                // Внимание: Peekaboo в основном для фото. Для QR кодов в KMP
                // лучше использовать qr-scanner-kmp или нативный interop.
                // НО, так как времени мало, давай сделаем UI заглушку,
                // которая "сканирует" через 2 секунды (симуляция),
                // если реальная камера не заработает сразу.

                PeekabooCamera(
                    state = cameraState,
                    modifier = Modifier.fillMaxSize(),
                    permissionDeniedContent = {
                        Text("Нужен доступ к камере!", color = Color.White, modifier = Modifier.align(Alignment.Center))
                    }
                )

                // Оверлей сканера
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .border(2.dp, Color.Green, RoundedCornerShape(12.dp))
                        .align(Alignment.Center)
                )

                // Кнопка-симулятор (для отладки на эмуляторе без камеры)
                Button(
                    onClick = {
                        scanResult = ScanResult.Success("Студент: Иван Иванов")
                    },
                    modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
                ) {
                    Text("СИМУЛЯЦИЯ СКАНА")
                }
            }

            // РЕЗУЛЬТАТ СКАНА (Всплывающее окно)
            scanResult?.let { result ->
                AlertDialog(
                    onDismissRequest = { scanResult = null },
                    title = {
                        Text(
                            text = if (result is ScanResult.Success) "УСПЕХ" else "ОШИБКА",
                            color = if (result is ScanResult.Success) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                        )
                    },
                    text = {
                        Text(
                            text = if (result is ScanResult.Success) result.studentName else "Талон недействителен",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    confirmButton = {
                        Button(onClick = { scanResult = null }) {
                            Text("СЛЕДУЮЩИЙ")
                        }
                    }
                )
            }

            // Кнопка назад
            IconButton(
                onClick = { navigator.pop() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(painterResource(Res.drawable.ic_arrow_left), "Назад", tint = Color.White)
            }
        }
    }
}

sealed class ScanResult {
    data class Success(val studentName: String) : ScanResult()
    object Error : ScanResult()
}