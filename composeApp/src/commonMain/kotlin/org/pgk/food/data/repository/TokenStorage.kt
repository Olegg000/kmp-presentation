package org.pgk.food.data.repository

interface TokenStorage {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
    fun getCurrentUserId(): String?
    fun saveUserId(userId: String)
}