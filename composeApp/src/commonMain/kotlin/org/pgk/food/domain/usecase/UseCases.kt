//package org.pgk.food.domain.usecase
//
//import org.pgk.food.data.repository.AuthRepository
//import org.pgk.food.data.repository.TransactionRepository
//import org.pgk.food.domain.model.MealType
//import org.pgk.food.domain.model.User
//import org.pgk.food.utils.QRCodeBitmap
//import org.pgk.food.utils.QRGenerator
//import org.pgk.food.utils.TOTPValidator
//
//class GenerateQRUseCase(
//    private val qrGenerator: QRGenerator,
//    private val totpValidator: TOTPValidator
//) {
//    operator fun invoke(userId: String, mealType: MealType, privateKey: String, serverTimeDelta: Long = 0): QRCodeBitmap {
//        return qrGenerator.generateQRCode(userId, mealType, privateKey, totpValidator, serverTimeDelta)
//    }
//}
//
//class ValidateQRUseCase(
//    private val qrGenerator: QRGenerator,
//    private val totpValidator: TOTPValidator,
//    private val transactionRepository: TransactionRepository
//) {
//    suspend operator fun invoke(qrText: String, publicKey: String, serverTimeDelta: Long = 0): Result<Boolean> = runCatching {
//        // Твоя логика валидации тут
//        true
//    }
//}
//
//class LoginUseCase(private val authRepository: AuthRepository) {
//    suspend operator fun invoke(l: String, p: String): Result<User> = authRepository.login(l, p)
//}
//
//// Заглушки для Koin, чтобы не падал
//class SyncTransactionsUseCase(repo: TransactionRepository)
//class CreateUserUseCase(repo: Any)
//class GetRosterUseCase(repo: Any)
//class UpdateRosterUseCase(repo: Any)