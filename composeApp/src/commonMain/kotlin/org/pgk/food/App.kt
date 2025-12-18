package org.pgk.food

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pgk.food.presentation.components.PgkRefStyleShowcase
import org.pgk.food.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        PgkRefStyleShowcase()
    }
}