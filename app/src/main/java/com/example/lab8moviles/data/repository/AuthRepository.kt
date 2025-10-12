package com.example.lab8moviles.data.repository

import com.example.lab8moviles.data.datastore.UserPreferencesManager
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val userPreferencesManager: UserPreferencesManager
) {

    val userName: Flow<String?> = userPreferencesManager.userName

    suspend fun login(name: String) {
        userPreferencesManager.saveUserName(name)
    }

    suspend fun logout() {
        userPreferencesManager.clearUserName()
    }
}