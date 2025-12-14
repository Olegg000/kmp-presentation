package org.pgk.food.presentation.screens.admin

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AdminDashboardState(
    val totalStudents: Int = 0,
    val totalGroups: Int = 0,
    val mealsToday: Int = 0,
    val mealsWeek: Int = 0
)

class AdminViewModel : ScreenModel {
    private val _state = MutableStateFlow(
        AdminDashboardState(
            totalStudents = 156,
            totalGroups = 8,
            mealsToday = 89,
            mealsWeek = 512
        )
    )
    val state: StateFlow<AdminDashboardState> = _state.asStateFlow()

    fun logout() {
        // Очистка токенов или сессии
    }
}