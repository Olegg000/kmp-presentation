package org.pgk.food.presentation.screens.student

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.pgk.food.domain.model.Ticket
import org.pgk.food.utils.QRCodeBitmap

class QRCodeScreen(private val ticket: Ticket) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<StudentHomeViewModel>() // Используем ту же VM для генерации

        LaunchedEffect(Unit) {
            viewModel.generateQR(ticket.mealType)
        }

        val qrState by viewModel.qrState.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("QR Код") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBack, "Назад")
                        }
                    }
                )
            }
        ) { padding ->
            QRCodeContent(
                qrState = qrState,
                ticket = ticket,
                onRefresh = { viewModel.generateQR(ticket.mealType) },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun QRCodeContent(
    qrState: QRState,
    ticket: Ticket,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        while (true) {
            delay(30_000)
            onRefresh()
        }
    }
    Column(modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(ticket.mealType.name, style = MaterialTheme.typography.headlineMedium)
        Text(ticket.date)
        Spacer(Modifier.height(32.dp))
        Card(Modifier.size(300.dp)) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (qrState.qrBitmap != null) {
                    QRCodeImage(qrState.qrBitmap!!)
                } else {
                    CircularProgressIndicator()
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        // Таймер упрощенно
        Text("Обновится через 30 сек")
    }
}

@Composable
fun QRCodeImage(qrBitmap: QRCodeBitmap) {
    // Рисуем QR через Canvas, так как ImageBitmap требует платформенного кода
    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val matrix = qrBitmap.bitMatrix
        val width = matrix.width
        val height = matrix.height
        val cellWidth = size.width / width
        val cellHeight = size.height / height

        for (x in 0 until width) {
            for (y in 0 until height) {
                if (matrix[x, y]) {
                    drawRect(
                        color = Color.Black,
                        topLeft = androidx.compose.ui.geometry.Offset(x * cellWidth, y * cellHeight),
                        size = androidx.compose.ui.geometry.Size(cellWidth + 1, cellHeight + 1) // +1 чтобы убрать щели
                    )
                }
            }
        }
    }
}