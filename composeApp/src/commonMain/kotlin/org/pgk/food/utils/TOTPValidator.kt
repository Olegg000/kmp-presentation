package org.pgk.food.utils

import kotlinx.datetime.Clock
import org.pgk.food.domain.model.MealType
import kotlin.random.Random

class TOTPValidator {
    companion object {
        const val TIME_WINDOW_SECONDS = 30L
        const val ALLOWED_TIME_DRIFT = 2
    }

    fun getCurrentTimeSlot(serverTimeDelta: Long = 0): Long {
        val now = Clock.System.now().epochSeconds + serverTimeDelta
        return now / TIME_WINDOW_SECONDS
    }

    fun isTimestampValid(qrTimestamp: Long, serverTimeDelta: Long = 0): Boolean {
        val currentSlot = getCurrentTimeSlot(serverTimeDelta)
        val qrSlot = qrTimestamp / TIME_WINDOW_SECONDS
        val diff = kotlin.math.abs(currentSlot - qrSlot)
        return diff <= ALLOWED_TIME_DRIFT
    }

    fun signQRPayload(
        userId: String,
        timestamp: Long,
        mealType: MealType,
        nonce: String,
        privateKey: String
    ): String {
        val data = "$userId:$timestamp:${mealType.name}:$nonce"
        val combined = data + privateKey
        return combined.hashCode().toString(16).padStart(16, '0')
    }

    fun verifySignature(
        userId: String,
        timestamp: Long,
        mealType: MealType,
        nonce: String,
        signature: String,
        publicKey: String
    ): Boolean {
        val data = "$userId:$timestamp:${mealType.name}:$nonce"
        val combined = data + publicKey
        val expectedSignature = combined.hashCode().toString(16).padStart(16, '0')
        return signature == expectedSignature
    }

    fun generateNonce(): String {
        val bytes = ByteArray(16)
        Random.Default.nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}