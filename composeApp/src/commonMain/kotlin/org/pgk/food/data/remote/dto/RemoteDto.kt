//package org.pgk.food.data.remote.dto
//
//import kotlinx.serialization.Serializable
//
//@Serializable
//data class LoginRequest(
//    val login: String,
//    val password: String
//)
//
//@Serializable
//data class AuthResponse(
//    val token: String,
//    val roles: List<String>,
//    val privateKey: String,
//    val publicKey: String
//)
//
//@Serializable
//data class UserDto(
//    val id: String,
//    val login: String,
//    val name: String,
//    val surname: String,
//    val fatherName: String,
//    val roles: List<String>,
//    val groupId: Int? = null
//)
//
//@Serializable
//data class CreateUserRequest(
//    val roles: List<String>,
//    val name: String,
//    val surname: String,
//    val fatherName: String,
//    val groupId: Int? = null
//)
//
//@Serializable
//data class UserCredentialsResponse(
//    val userId: String,
//    val login: String,
//    val passwordClearText: String,
//    val fullName: String
//)
//
//@Serializable
//data class GroupDto(
//    val id: Int,
//    val name: String,
//    val curatorId: String?,
//    val curatorName: String?,
//    val curatorSurname: String?,
//    val curatorFatherName: String?,
//    val studentCount: Int
//)
//
//@Serializable
//data class ValidateQRRequest(
//    val userId: String,
//    val timestamp: Long,
//    val mealType: String,
//    val nonce: String,
//    val signature: String
//)
//
//@Serializable
//data class ValidateQRResponse(
//    val isValid: Boolean,
//    val studentName: String?,
//    val groupName: String? = null,
//    val errorMessage: String? = null,
//    val errorCode: String? = null
//)
//
//@Serializable
//data class TransactionSyncItem(
//    val studentId: String,
//    val timestamp: String, // ISO-8601
//    val mealType: String,
//    val transactionHash: String? = null
//)
//
//@Serializable
//data class SyncResponse(
//    val successCount: Int,
//    val errors: List<String>
//)
//
//@Serializable
//data class DayPermissionDto(
//    val date: String,
//    val isBreakfast: Boolean,
//    val isLunch: Boolean,
//    val isDinner: Boolean,
//    val isSnack: Boolean = false,
//    val isSpecial: Boolean = false,
//    val reason: String? = null
//)
//
//@Serializable
//data class UpdateRosterRequest(
//    val studentId: String,
//    val permissions: List<DayPermissionDto>
//)
//
//@Serializable
//data class StudentRosterRow(
//    val studentId: String,
//    val fullName: String,
//    val days: List<DayPermissionDto>
//)
//
//@Serializable
//data class TimeResponse(
//    val timestamp: Long,
//    val iso8601: String
//)
//
//@Serializable
//data class DailyReportResponse(
//    val date: String,
//    val total: Int,
//    val breakfast: Int,
//    val lunch: Int,
//    val dinner: Int
//)