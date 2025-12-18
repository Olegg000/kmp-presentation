//package org.pgk.food.presentation.navigation
//
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.navigator.Navigator
//import org.pgk.food.domain.model.Role
//import org.pgk.food.presentation.screens.auth.LoginScreen
//import org.pgk.food.presentation.screens.student.StudentHomeScreen
//import org.pgk.food.presentation.screens.cook.CookHomeScreen // Убедись, что создал этот экран
//import org.pgk.food.presentation.screens.registrar.RegistrarHomeScreen
//import org.pgk.food.presentation.screens.curator.CuratorHomeScreen
//import org.pgk.food.presentation.screens.admin.AdminDashboardScreen
//
//fun Navigator.navigateToHome(roles: List<Role>) {
//    val homeScreen: Screen = when {
//        roles.contains(Role.ADMIN) -> AdminDashboardScreen()
//        roles.contains(Role.CHEF) -> CookHomeScreen() // Или ScannerScreen
//        roles.contains(Role.REGISTRAR) -> RegistrarHomeScreen()
//        roles.contains(Role.CURATOR) -> CuratorHomeScreen()
//        roles.contains(Role.STUDENT) -> StudentHomeScreen()
//        else -> LoginScreen()
//    }
//    replaceAll(homeScreen)
//}
//
//fun Navigator.logout() {
//    replaceAll(LoginScreen())
//}