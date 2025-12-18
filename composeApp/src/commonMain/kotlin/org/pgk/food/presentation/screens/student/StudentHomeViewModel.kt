//package org.pgk.food.presentation.screens.student
//
//import cafe.adriel.voyager.core.model.ScreenModel
//import cafe.adriel.voyager.core.model.screenModelScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import org.pgk.food.domain.model.MealType
//import org.pgk.food.domain.model.Ticket
//import org.pgk.food.domain.usecase.GenerateQRUseCase
//import org.pgk.food.utils.QRCodeBitmap
//
//data class StudentHomeState(
//    val tickets: List<Ticket> = emptyList(),
//    val isLoading: Boolean = false,
//    val error: String? = null
//)
//
//data class QRState(
//    val qrBitmap: QRCodeBitmap? = null,
//    val isLoading: Boolean = false,
//    val error: String? = null
//)
//
//class StudentHomeViewModel(
//    private val generateQRUseCase: GenerateQRUseCase,
//    private val tokenStorage: org.pgk.food.data.repository.TokenStorage, // Добавил storage для ID
//    private val ticketRepository: org.pgk.food.data.repository.TicketRepository // Добавил репо
//) : ScreenModel {
//    private val _state = MutableStateFlow(StudentHomeState())
//    val state: StateFlow<StudentHomeState> = _state.asStateFlow()
//
//    private val _qrState = MutableStateFlow(QRState())
//    val qrState: StateFlow<QRState> = _qrState.asStateFlow()
//
//    init {
//        loadTickets()
//    }
//
//    private fun loadTickets() {
//        screenModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true)
//            delay(500) // Mock loading
//            _state.value = _state.value.copy(
//                isLoading = false,
//                tickets = listOf(
//                    Ticket("1", "user1", MealType.BREAKFAST, "2025-12-14", false),
//                    Ticket("2", "user1", MealType.LUNCH, "2025-12-14", false),
//                    Ticket("3", "user1", MealType.DINNER, "2025-12-14", false)
//                )
//            )
//        }
//    }
//
//    fun generateQR(mealType: MealType) {
//        screenModelScope.launch {
//            _qrState.value = _qrState.value.copy(isLoading = true)
//            try {
//                // В реале брать ID и ключ из User/TokenStorage
//                val qrBitmap = generateQRUseCase(
//                    userId = "temp_user_id",
//                    mealType = mealType,
//                    privateKey = "temp_private_key",
//                    serverTimeDelta = 0
//                )
//                _qrState.value = _qrState.value.copy(isLoading = false, qrBitmap = qrBitmap)
//            } catch (e: Exception) {
//                _qrState.value = _qrState.value.copy(isLoading = false, error = e.message)
//            }
//        }
//    }
//
//    fun logout() {
//        tokenStorage.clearToken()
//    }
//}