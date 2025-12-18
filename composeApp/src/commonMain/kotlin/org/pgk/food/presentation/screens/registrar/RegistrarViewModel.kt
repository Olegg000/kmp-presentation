//package org.pgk.food.presentation.screens.registrar
//
//import cafe.adriel.voyager.core.model.ScreenModel
//import cafe.adriel.voyager.core.model.screenModelScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import org.pgk.food.data.remote.dto.UserCredentialsResponse
//import org.pgk.food.domain.model.Role
//
//// Состояние выносим отдельно, чтобы не засорять VM
//data class CreateUserState(
//    val name: String = "",
//    val surname: String = "",
//    val fatherName: String = "",
//    val selectedRoles: Set<Role> = setOf(Role.STUDENT),
//    val groupId: String = "",
//    val isLoading: Boolean = false,
//    val error: String? = null,
//    val createdUser: UserCredentialsResponse? = null
//) {
//    val isValid: Boolean
//        get() = name.isNotBlank() &&
//                surname.isNotBlank() &&
//                fatherName.isNotBlank() &&
//                selectedRoles.isNotEmpty()
//}
//
//class RegistrarViewModel : ScreenModel {
//    private val _createUserState = MutableStateFlow(CreateUserState())
//    val createUserState: StateFlow<CreateUserState> = _createUserState.asStateFlow()
//
//    fun updateName(name: String) {
//        _createUserState.value = _createUserState.value.copy(name = name)
//    }
//
//    fun updateSurname(surname: String) {
//        _createUserState.value = _createUserState.value.copy(surname = surname)
//    }
//
//    fun updateFatherName(fatherName: String) {
//        _createUserState.value = _createUserState.value.copy(fatherName = fatherName)
//    }
//
//    fun toggleRole(role: Role) {
//        val current = _createUserState.value.selectedRoles
//        _createUserState.value = _createUserState.value.copy(
//            selectedRoles = if (current.contains(role)) {
//                current - role
//            } else {
//                current + role
//            }
//        )
//    }
//
//    fun updateGroupId(groupId: String) {
//        _createUserState.value = _createUserState.value.copy(groupId = groupId)
//    }
//
//    fun createUser() {
//        screenModelScope.launch {
//            _createUserState.value = _createUserState.value.copy(isLoading = true)
//
//            // Эмуляция запроса. Здесь вставь реальный вызов UseCase
//            delay(1000)
//
//            val mockResponse = UserCredentialsResponse(
//                userId = "123",
//                login = "student123",
//                passwordClearText = "temp_password_123",
//                fullName = "${_createUserState.value.surname} ${_createUserState.value.name}"
//            )
//
//            _createUserState.value = _createUserState.value.copy(
//                isLoading = false,
//                createdUser = mockResponse
//            )
//        }
//    }
//
//    fun clearCreatedUser() {
//        _createUserState.value = CreateUserState()
//    }
//}