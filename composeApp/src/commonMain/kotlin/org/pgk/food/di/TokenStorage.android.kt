package org.pgk.food.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.pgk.food.data.repository.TokenStorage

// Actual factory
actual fun createTokenStorage(): TokenStorage = AndroidTokenStorage()

class AndroidTokenStorage : TokenStorage, KoinComponent {
    private val context: Context by inject()
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("pgk_food_prefs", Context.MODE_PRIVATE)
    }
    override fun saveToken(token: String) { prefs.edit().putString("auth_token", token).apply() }
    override fun getToken(): String? = prefs.getString("auth_token", null)
    override fun clearToken() { prefs.edit().clear().apply() }
    override fun getCurrentUserId(): String? = prefs.getString("user_id", null)
    override fun saveUserId(userId: String) { prefs.edit().putString("user_id", userId).apply() }
}