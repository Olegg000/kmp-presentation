//package org.pgk.food.data.repository
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.flowOf
//import org.pgk.food.data.local.dao.UserDao
//import org.pgk.food.data.local.dao.TicketDao
//import org.pgk.food.data.local.dao.TransactionDao
//import org.pgk.food.data.local.entities.*
//import org.pgk.food.data.remote.ApiClient
//import org.pgk.food.data.remote.dto.*
//import org.pgk.food.domain.model.*
//
//class AuthRepository(
//    private val api: ApiClient,
//    private val userDao: UserDao,
//    private val tokenStorage: TokenStorage
//) {
//    suspend fun login(login: String, password: String): Result<User> {
//        return api.login(login, password).mapCatching { response ->
//            // Сохраняем токен
//            tokenStorage.saveToken(response.token)
//
//            // Сохраняем пользователя локально
//            val user = User(
//                id = "temp", // Нужно получить userId с сервера (добавь в AuthResponse)
//                login = login,
//                name = "",
//                surname = "",
//                fatherName = "",
//                roles = response.roles.map { Role.valueOf(it) },
//                groupId = null,
//                privateKey = response.privateKey,
//                publicKey = response.publicKey
//            )
//
//            userDao.insert(user.toEntity())
//            user
//        }
//    }
//
//    suspend fun logout() {
//        tokenStorage.clearToken()
//        userDao.clearAll()
//    }
//
//    fun getCurrentUser(): Flow<User?> {
//        val userId = tokenStorage.getCurrentUserId() ?: return flowOf(null)
//        return userDao.observeUser(userId).map { it?.toDomain() }
//    }
//}
//
//class TicketRepository(
//    private val ticketDao: TicketDao
//) {
//    fun getRecentTickets(studentId: String): Flow<List<Ticket>> {
//        return ticketDao.observeRecentTickets(studentId).map { list ->
//            list.map { it.toDomain() }
//        }
//    }
//
//    suspend fun getTicketsForDate(studentId: String, date: String): List<Ticket> {
//        return ticketDao.getTicketsForDate(studentId, date).map { it.toDomain() }
//    }
//}
//
//class TransactionRepository(
//    private val api: ApiClient,
//    private val transactionDao: TransactionDao
//) {
//    suspend fun saveTransaction(
//        studentId: String,
//        timestamp: Long,
//        mealType: MealType,
//        qrHash: String
//    ) {
//        val transaction = TransactionEntity(
//            id = java.util.UUID.randomUUID().toString(),
//            studentId = studentId,
//            timestamp = timestamp,
//            mealType = mealType.name,
//            qrHash = qrHash,
//            syncedToServer = false
//        )
//        transactionDao.insert(transaction)
//    }
//
//    suspend fun syncPendingTransactions(): Result<SyncResponse> {
//        val pending = transactionDao.getUnsyncedTransactions()
//        if (pending.isEmpty()) return Result.success(SyncResponse(0, emptyList()))
//
//        val items = pending.map {
//            TransactionSyncItem(
//                studentId = it.studentId,
//                timestamp = kotlinx.datetime.Instant.fromEpochMilliseconds(it.timestamp).toString(),
//                mealType = it.mealType,
//                transactionHash = it.qrHash
//            )
//        }
//
//        return api.syncTransactionsBatch(items).onSuccess {
//            transactionDao.markAsSynced(pending.map { it.id })
//        }
//    }
//}
//
//// Mapper extensions
//private fun UserEntity.toDomain() = User(
//    id = id,
//    login = login,
//    name = name,
//    surname = surname,
//    fatherName = fatherName,
//    roles = roles.map { Role.valueOf(it) },
//    groupId = groupId,
//    privateKey = privateKey,
//    publicKey = publicKey
//)
//
//private fun User.toEntity() = UserEntity(
//    id = id,
//    login = login,
//    name = name,
//    surname = surname,
//    fatherName = fatherName,
//    roles = roles.map { it.name },
//    groupId = groupId,
//    privateKey = privateKey,
//    publicKey = publicKey
//)
//
//private fun TicketEntity.toDomain() = Ticket(
//    id = id,
//    studentId = studentId,
//    mealType = MealType.valueOf(mealType),
//    date = date,
//    isUsed = isUsed
//)