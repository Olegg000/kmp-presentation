//package org.pgk.food.presentation.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import org.pgk.food.data.remote.dto.UserCredentialsResponse
//
//// Твои правильные импорты
//import org.jetbrains.compose.resources.painterResource
//import org.pgk.food.resources.Res
//import org.pgk.food.resources.ic_check_circle
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun QuickActionCard(
//    title: String,
//    iconPainter: Painter,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        onClick = onClick,
//        modifier = modifier,
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                painter = iconPainter,
//                contentDescription = null,
//                modifier = Modifier.size(40.dp),
//                tint = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//            Spacer(Modifier.height(8.dp))
//            Text(
//                title,
//                style = MaterialTheme.typography.titleSmall,
//                fontWeight = FontWeight.SemiBold
//            )
//        }
//    }
//}
//
//@Composable
//fun CreatedUserDialog(
//    user: UserCredentialsResponse,
//    onDismiss: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        icon = {
//            Icon(
//                painter = painterResource(Res.drawable.ic_check_circle),
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.primary
//            )
//        },
//        title = { Text("Пользователь создан") },
//        text = {
//            Column {
//                Text("Запишите эти данные:", fontWeight = FontWeight.Bold)
//                Spacer(Modifier.height(8.dp))
//                Text("Логин: ${user.login}")
//                Text("Пароль: ${user.passwordClearText}")
//                Spacer(Modifier.height(8.dp))
//                Text(
//                    "Пароль показывается только один раз!",
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//        },
//        confirmButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Понятно")
//            }
//        }
//    )
//}