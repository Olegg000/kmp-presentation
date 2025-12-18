//package org.pgk.food.utils
//
//import com.google.zxing.BarcodeFormat
//import com.google.zxing.qrcode.QRCodeWriter
//import com.google.zxing.common.BitMatrix
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//import org.pgk.food.domain.model.MealType
//
//@Serializable
//data class QRData(
//    val userId: String,
//    val timestamp: Long,
//    val mealType: String,
//    val nonce: String,
//    val signature: String
//)
//
//data class QRCodeBitmap(
//    val bitMatrix: BitMatrix,
//    val rawData: String
//)
//
//class QRGenerator {
//    private val writer = QRCodeWriter()
//    private val json = Json { ignoreUnknownKeys = true }
//
//    fun generateQRCode(
//        userId: String,
//        mealType: MealType,
//        privateKey: String,
//        totpValidator: TOTPValidator,
//        serverTimeDelta: Long = 0
//    ): QRCodeBitmap {
//        val timestamp = totpValidator.getCurrentTimeSlot(serverTimeDelta) * TOTPValidator.TIME_WINDOW_SECONDS
//        val nonce = totpValidator.generateNonce()
//        val signature = totpValidator.signQRPayload(userId, timestamp, mealType, nonce, privateKey)
//
//        val data = QRData(
//            userId = userId,
//            timestamp = timestamp,
//            mealType = mealType.name,
//            nonce = nonce,
//            signature = signature
//        )
//
//        val qrText = json.encodeToString(data)
//        // Генерируем QR 512x512
//        val bitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, 512, 512)
//
//        return QRCodeBitmap(bitMatrix, qrText)
//    }
//
//    fun parseQRCode(qrText: String): Result<QRData> = runCatching {
//        json.decodeFromString<QRData>(qrText)
//    }
//}