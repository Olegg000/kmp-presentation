//package org.pgk.food.presentation.screens.auth
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.model.ScreenModel
//import cafe.adriel.voyager.core.model.screenModelScope
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.koin.koinScreenModel
//import cafe.adriel.voyager.navigator.LocalNavigator
//import cafe.adriel.voyager.navigator.currentOrThrow
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import org.pgk.food.domain.model.Role
//import org.pgk.food.presentation.navigation.navigateToHome
//
//class LoginScreen : Screen {
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        val viewModel = koinScreenModel<LoginViewModel>()
//        val state by viewModel.state.collectAsState()
//
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Card(
//                modifier = Modifier.fillMaxWidth().padding(24.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(
//                    modifier = Modifier.padding(24.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    Text("Вход в систему", style = MaterialTheme.typography.headlineSmall)
//
//                    OutlinedTextField(
//                        value = state.login,
//                        onValueChange = viewModel::onLoginChange,
//                        label = { Text("Логин") },
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//
//                    OutlinedTextField(
//                        value = state.password,
//                        onValueChange = viewModel::onPasswordChange,
//                        label = { Text("Пароль") },
//                        visualTransformation = PasswordVisualTransformation(),
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//
//                    if (state.error != null) {
//                        Text(state.error!!, color = MaterialTheme.colorScheme.error)
//                    }
//
//                    Button(
//                        onClick = {
//                            viewModel.login { roles ->
//                                navigator.navigateToHome(roles)
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth().height(50.dp),
//                        enabled = !state.isLoading
//                    ) {
//                        if (state.isLoading) CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
//                        else Text("Войти")
//                    }
//                }
//            }
//        }
//    }
//}
//
//data class LoginState(
//    val login: String = "",
//    val password: String = "",
//    val isLoading: Boolean = false,
//    val error: String? = null
//)
//
//class LoginViewModel : ScreenModel {
//    private val _state = MutableStateFlow(LoginState())
//    val state = _state.asStateFlow()
//
//    fun onLoginChange(value: String) { _state.value = _state.value.copy(login = value) }
//    fun onPasswordChange(value: String) { _state.value = _state.value.copy(password = value) }
//
//    fun login(onSuccess: (List<Role>) -> Unit) {
//        screenModelScope.launch {
//            _state.value = _state.value.copy(isLoading = true, error = null)
//            delay(1000) // Имитация сети
//
//            // ПРОСТОЙ ХАРДКОД ДЛЯ ТЕСТА
//            val login = _state.value.login.lowercase().trim()
//            val roles = when {
//                login.contains("admin") -> listOf(Role.ADMIN)
//                login.contains("cook") -> listOf(Role.CHEF)
//                login.contains("curator") -> listOf(Role.CURATOR)
//                login.contains("reg") -> listOf(Role.REGISTRAR)
//                else -> listOf(Role.STUDENT) // По дефолту студент
//            }
//
//            _state.value = _state.value.copy(isLoading = false)
//            onSuccess(roles)
//        }
//    }
//}