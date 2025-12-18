//package org.pgk.food.di
//
//import org.pgk.food.data.repository.TokenStorage
//import platform.Foundation.NSUserDefaults
//
//actual fun createTokenStorage(): TokenStorage = IOSTokenStorage()
//
//class IOSTokenStorage : TokenStorage {
//    private val defaults = NSUserDefaults.standardUserDefaults
//    override fun saveToken(token: String) = defaults.setObject(token, "auth_token")
//    override fun getToken(): String? = defaults.stringForKey("auth_token")
//    override fun clearToken() { defaults.removeObjectForKey("auth_token"); defaults.removeObjectForKey("user_id") }
//    override fun getCurrentUserId(): String? = defaults.stringForKey("user_id")
//    override fun saveUserId(userId: String) = defaults.setObject(userId, "user_id")
//}