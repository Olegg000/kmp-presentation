//package org.pgk.food.presentation.screens.auth
//
//import cafe.adriel.voyager.core.model.ScreenModel
//import cafe.adriel.voyager.core.model.screenModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import org.pgk.food.domain.model.User
//import org.pgk.food.domain.usecase.LoginUseCase
//
//data class LoginState(
//    val login: String = "",
//    val password: String = "",
//    val isLoading: Boolean = false,
//    val error: String? = null,
//    val user: User? = null
//)
//
//class LoginViewModel(
//    private val loginUseCase: LoginUseCase
//) : ScreenModel {
//    private val _state = MutableStateFlow(LoginState())
//    val state: StateFlow<LoginState> = _state.asStateFlow()
//
//    fun updateLogin(login: String) { _state.value = _state.value.copy(login = login, error = null) }
//    fun updatePassword(password: String) { _state.value = _state.value.copy(password = password, error = null) }
//
//    fun login() {
//        if (_state.value.login.isBlank()) return
//        screenModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true)
//            loginUseCase(_state.value.login, _state.value.password)
//                .onSuccess { user -> _state.value = _state.value.copy(isLoading = false, user = user) }
//                .onFailure { e -> _state.value = _state.value.copy(isLoading = false, error = e.message) }
//        }
//    }
//}