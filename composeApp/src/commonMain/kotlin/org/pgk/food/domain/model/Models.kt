//package org.pgk.food.domain.model
//
//enum class Role {
//    STUDENT, CHEF, REGISTRAR, CURATOR, ADMIN;
//
//    companion object {
//        fun fromString(s: String) = entries.find { it.name == s } ?: STUDENT
//    }
//}
//
//enum class MealType {
//    BREAKFAST, LUNCH, DINNER, SNACK, SPECIAL
//}
//
//data class User(
//    val id: String,
//    val login: String,
//    val name: String,
//    val surname: String,
//    val fatherName: String,
//    val roles: List<Role>,
//    val groupId: Int?,
//    val privateKey: String,
//    val publicKey: String
//) {
//    val fullName: String get() = "$surname $name $fatherName"
//
//    fun hasRole(role: Role) = roles.contains(role)
//}
//
//data class Ticket(
//    val id: String,
//    val studentId: String,
//    val mealType: MealType,
//    val date: String,
//    val isUsed: Boolean = false
//)
//
//data class QRPayload(
//    val userId: String,
//    val timestamp: Long,
//    val mealType: MealType,
//    val nonce: String,
//    val signature: String
//)