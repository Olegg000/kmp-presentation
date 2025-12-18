//package org.pgk.food.data.local.dao
//
//import androidx.room.*
//import kotlinx.coroutines.flow.Flow
//import org.pgk.food.data.local.entities.*
//
//@Dao
//interface UserDao {
//    @Query("SELECT * FROM users WHERE id = :id")
//    suspend fun getById(id: String): UserEntity?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(user: UserEntity)
//
//    @Query("DELETE FROM users")
//    suspend fun clearAll()
//
//    @Query("SELECT * FROM users WHERE id = :id")
//    fun observeUser(id: String): Flow<UserEntity?>
//}
//
//@Dao
//interface TicketDao {
//    @Query("SELECT * FROM tickets WHERE studentId = :studentId AND date = :date")
//    suspend fun getTicketsForDate(studentId: String, date: String): List<TicketEntity>
//
//    @Query("SELECT * FROM tickets WHERE studentId = :studentId ORDER BY date DESC LIMIT 30")
//    fun observeRecentTickets(studentId: String): Flow<List<TicketEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(tickets: List<TicketEntity>)
//
//    @Query("UPDATE tickets SET isUsed = 1, usedAt = :timestamp WHERE id = :ticketId")
//    suspend fun markAsUsed(ticketId: String, timestamp: Long)
//
//    @Query("DELETE FROM tickets WHERE date < :cutoffDate")
//    suspend fun deleteOldTickets(cutoffDate: String)
//}
//
//@Dao
//interface GroupDao {
//    @Query("SELECT * FROM groups")
//    suspend fun getAll(): List<GroupEntity>
//
//    @Query("SELECT * FROM groups WHERE id = :id")
//    suspend fun getById(id: Int): GroupEntity?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(groups: List<GroupEntity>)
//
//    @Query("DELETE FROM groups")
//    suspend fun clearAll()
//}
//
//@Dao
//interface TransactionDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(transaction: TransactionEntity)
//
//    @Query("SELECT * FROM transactions WHERE syncedToServer = 0")
//    suspend fun getUnsyncedTransactions(): List<TransactionEntity>
//
//    @Query("UPDATE transactions SET syncedToServer = 1 WHERE id IN (:ids)")
//    suspend fun markAsSynced(ids: List<String>)
//
//    @Query("SELECT * FROM transactions WHERE timestamp > :sinceTimestamp ORDER BY timestamp DESC")
//    fun observeRecentTransactions(sinceTimestamp: Long): Flow<List<TransactionEntity>>
//
//    @Query("DELETE FROM transactions WHERE timestamp < :cutoffTimestamp")
//    suspend fun deleteOldTransactions(cutoffTimestamp: Long)
//}
//
//@Dao
//interface RosterDao {
//    @Query("SELECT * FROM roster_permissions WHERE studentId = :studentId AND date = :date")
//    suspend fun getPermissionsForDate(studentId: String, date: String): RosterPermissionEntity?
//
//    @Query("SELECT * FROM roster_permissions WHERE studentId = :studentId AND date BETWEEN :startDate AND :endDate")
//    suspend fun getPermissionsForWeek(studentId: String, startDate: String, endDate: String): List<RosterPermissionEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(permissions: List<RosterPermissionEntity>)
//
//    @Query("DELETE FROM roster_permissions WHERE date < :cutoffDate")
//    suspend fun deleteOldPermissions(cutoffDate: String)
//}