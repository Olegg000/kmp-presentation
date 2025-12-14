package org.pgk.food.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.pgk.food.data.local.dao.*
import org.pgk.food.data.local.entities.*

@Database(
    entities = [
        UserEntity::class,
        TicketEntity::class,
        GroupEntity::class,
        TransactionEntity::class,
        RosterPermissionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun ticketDao(): TicketDao
    abstract fun groupDao(): GroupDao
    abstract fun transactionDao(): TransactionDao
    abstract fun rosterDao(): RosterDao
}

// Expect function for DB Builder
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

fun createDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}