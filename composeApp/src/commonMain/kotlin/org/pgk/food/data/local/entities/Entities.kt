//package org.pgk.food.data.local.entities
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.TypeConverter
//import androidx.room.TypeConverters
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//
//@Entity(tableName = "users")
//@TypeConverters(RoleListConverter::class)
//data class UserEntity(
//    @PrimaryKey val id: String,
//    val login: String,
//    val name: String,
//    val surname: String,
//    val fatherName: String,
//    val roles: List<String>, // ["STUDENT", "CURATOR"]
//    val groupId: Int?,
//    val privateKey: String,
//    val publicKey: String
//)
//
//@Entity(tableName = "tickets")
//data class TicketEntity(
//    @PrimaryKey val id: String,
//    val studentId: String,
//    val mealType: String, // BREAKFAST, LUNCH, DINNER, SNACK, SPECIAL
//    val date: String, // ISO-8601: "2025-12-14"
//    val isUsed: Boolean = false,
//    val usedAt: Long? = null // timestamp
//)
//
//@Entity(tableName = "groups")
//data class GroupEntity(
//    @PrimaryKey val id: Int,
//    val name: String,
//    val curatorId: String?,
//    val curatorName: String?
//)
//
//@Entity(tableName = "transactions")
//data class TransactionEntity(
//    @PrimaryKey val id: String,
//    val studentId: String,
//    val timestamp: Long,
//    val mealType: String,
//    val qrHash: String,
//    val syncedToServer: Boolean = false
//)
//
//@Entity(tableName = "roster_permissions")
//data class RosterPermissionEntity(
//    @PrimaryKey(autoGenerate = true) val id: Long = 0,
//    val studentId: String,
//    val date: String,
//    val isBreakfast: Boolean = false,
//    val isLunch: Boolean = false,
//    val isDinner: Boolean = false,
//    val isSnack: Boolean = false,
//    val isSpecial: Boolean = false,
//    val reason: String? = null
//)
//
//// Конвертеры для Room
//class RoleListConverter {
//    @TypeConverter
//    fun fromString(value: String): List<String> = Json.decodeFromString(value)
//
//    @TypeConverter
//    fun toString(list: List<String>): String = Json.encodeToString(list)
//}