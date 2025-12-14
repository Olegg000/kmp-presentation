package org.pgk.food.presentation.screens.registrar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import org.pgk.food.domain.model.Role
import org.pgk.food.presentation.components.CreatedUserDialog

import org.pgk.food.resources.Res
import org.pgk.food.resources.*

class CreateUserScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<RegistrarViewModel>()
        val state by viewModel.createUserState.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Добавить пользователя") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(painterResource(Res.drawable.ic_arrow_left), "Назад")
                        }
                    }
                )
            }
        ) { padding ->
            CreateUserContent(
                state = state,
                onNameChange = viewModel::updateName,
                onSurnameChange = viewModel::updateSurname,
                onFatherNameChange = viewModel::updateFatherName,
                onRoleToggle = viewModel::toggleRole,
                onGroupIdChange = viewModel::updateGroupId,
                onCreateClick = viewModel::createUser,
                modifier = Modifier.padding(padding)
            )
        }

        // Success Dialog
        state.createdUser?.let { user ->
            CreatedUserDialog(
                user = user,
                onDismiss = {
                    viewModel.clearCreatedUser()
                    navigator.pop()
                }
            )
        }
    }
}

@Composable
fun CreateUserContent(
    state: CreateUserState,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onFatherNameChange: (String) -> Unit,
    onRoleToggle: (Role) -> Unit,
    onGroupIdChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Личные данные", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        item {
            OutlinedTextField(
                value = state.surname,
                onValueChange = onSurnameChange,
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item {
            OutlinedTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item {
            OutlinedTextField(
                value = state.fatherName,
                onValueChange = onFatherNameChange,
                label = { Text("Отчество") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item {
            Spacer(Modifier.height(8.dp))
            Text("Роли", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    Role.STUDENT to "Студент",
                    Role.CURATOR to "Куратор",
                    Role.CHEF to "Повар",
                    Role.REGISTRAR to "Регистратор",
                    Role.ADMIN to "Администратор"
                ).forEach { (role, label) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = state.selectedRoles.contains(role),
                            onCheckedChange = { onRoleToggle(role) }
                        )
                        Text(label, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
        if (state.selectedRoles.contains(Role.STUDENT)) {
            item {
                OutlinedTextField(
                    value = state.groupId,
                    onValueChange = onGroupIdChange,
                    label = { Text("ID группы") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("Опционально для студентов") }
                )
            }
        }
        item {
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onCreateClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !state.isLoading && state.isValid,
                shape = RoundedCornerShape(12.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Создать пользователя")
                }
            }
        }
        state.error?.let { error ->
            item {
                Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}