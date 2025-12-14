package org.pgk.food.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.pgk.food.data.remote.dto.*

class ApiClient(
    private val baseUrl: String = "https://food.pgk.apis.alspio.com/api/v1",
    private val tokenProvider: () -> String?
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }

        install(DefaultRequest) {
            url(baseUrl)
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
        }
    }

    // ========================================================================
    // AUTH API
    // ========================================================================

    suspend fun login(login: String, password: String): Result<AuthResponse> = runCatching {
        client.post("/auth/login") {
            setBody(LoginRequest(login, password))
        }.body()
    }

    // ========================================================================
    // TIME SYNC API (для TOTP)
    // ========================================================================

    suspend fun getServerTime(): Result<TimeResponse> = runCatching {
        client.get("/time/current").body()
    }

    // ========================================================================
    // QR VALIDATION API
    // ========================================================================

    suspend fun validateQROnline(request: ValidateQRRequest): Result<ValidateQRResponse> = runCatching {
        client.post("/qr/validate") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            setBody(request)
        }.body()
    }

    suspend fun validateQROffline(request: ValidateQRRequest): Result<ValidateQRResponse> = runCatching {
        client.post("/qr/validate-offline") {
            setBody(request)
        }.body()
    }

    // ========================================================================
    // TRANSACTIONS API (синхронизация сканов повара)
    // ========================================================================

    suspend fun syncTransactionsBatch(items: List<TransactionSyncItem>): Result<SyncResponse> = runCatching {
        client.post("/transactions/batch") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            setBody(items)
        }.body()
    }

    // ========================================================================
    // REGISTRAR API (регистрация студентов)
    // ========================================================================

    suspend fun createUser(request: CreateUserRequest): Result<UserCredentialsResponse> = runCatching {
        client.post("/registrator/users/create") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            setBody(request)
        }.body()
    }

    suspend fun resetPassword(userId: String): Result<UserCredentialsResponse> = runCatching {
        client.post("/registrator/users/$userId/reset-password") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
        }.body()
    }

    // ========================================================================
    // GROUPS API
    // ========================================================================

    suspend fun getAllGroups(): Result<List<GroupDto>> = runCatching {
        client.get("/groups") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
        }.body()
    }

    suspend fun setCurator(groupId: Int, curatorId: String): Result<GroupDto> = runCatching {
        client.put("/groups/$groupId/curator/$curatorId") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
        }.body()
    }

    suspend fun addStudentToGroup(groupId: Int, studentId: String): Result<Unit> = runCatching {
        client.post("/groups/$groupId/students/$studentId") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
        }
    }

    // ========================================================================
    // ROSTER API (табель питания)
    // ========================================================================

    suspend fun getRoster(date: String): Result<List<StudentRosterRow>> = runCatching {
        client.get("/roster") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            parameter("date", date)
        }.body()
    }

    suspend fun updateRoster(request: UpdateRosterRequest): Result<Unit> = runCatching {
        client.post("/roster") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            setBody(request)
        }
    }

    // ========================================================================
    // REPORTS API
    // ========================================================================

    suspend fun getDailyReport(date: String): Result<DailyReportResponse> = runCatching {
        client.get("/reports/daily") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            parameter("date", date)
        }.body()
    }

    suspend fun getWeeklyReport(startDate: String): Result<List<DailyReportResponse>> = runCatching {
        client.get("/reports/weekly") {
            bearerAuth(tokenProvider() ?: throw IllegalStateException("No token"))
            parameter("startDate", startDate)
        }.body()
    }
}