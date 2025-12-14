package org.pgk.food.di

import org.koin.dsl.module
import org.pgk.food.data.remote.ApiClient
import org.pgk.food.data.repository.*
import org.pgk.food.domain.usecase.*
import org.pgk.food.presentation.screens.auth.LoginViewModel
import org.pgk.food.presentation.screens.student.StudentHomeViewModel
import org.pgk.food.presentation.screens.admin.AdminViewModel
import org.pgk.food.presentation.screens.registrar.RegistrarViewModel
import org.pgk.food.utils.QRGenerator
import org.pgk.food.utils.TOTPValidator

val dataModule = module {
    // Database stubs (null for now)
    single<Any?> { null }

    single { ApiClient { get<TokenStorage>().getToken() } }
    single { createTokenStorage() }

    single { AuthRepository(get(), get(), get()) }
    single { TicketRepository(get()) }
    single { TransactionRepository(get(), get()) }
}

val domainModule = module {
    factory { LoginUseCase(get()) }
    factory { GenerateQRUseCase(get(), get()) }
    factory { ValidateQRUseCase(get(), get(), get()) }

    single { QRGenerator() }
    single { TOTPValidator() }
}

val presentationModule = module {
    factory { LoginViewModel(get()) }
    factory { StudentHomeViewModel(get(), get(), get()) }
    factory { AdminViewModel() }
    factory { RegistrarViewModel() }
}

val appModule = listOf(dataModule, domainModule, presentationModule)