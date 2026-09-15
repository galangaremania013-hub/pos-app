package com.workid.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "workid_prefs")

class EmployeeDataStore(private val context: Context) {
    
    private val dataStore = context.dataStore
    
    companion object {
        val EMPLOYEE_ID = stringPreferencesKey("employee_id")
        val EMPLOYEE_NAME = stringPreferencesKey("employee_name")
        val EMPLOYEE_EMAIL = stringPreferencesKey("employee_email")
        val EMPLOYEE_POSITION = stringPreferencesKey("employee_position")
        val EMPLOYEE_DEPARTMENT = stringPreferencesKey("employee_department")
        val EMPLOYEE_PROFILE_IMAGE = stringPreferencesKey("employee_profile_image")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        val PIN_CODE = stringPreferencesKey("pin_code")
    }
    
    val employeeId: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[EMPLOYEE_ID]
        }
    
    val employeeName: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[EMPLOYEE_NAME]
        }
    
    val isLoggedIn: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    
    val biometricEnabled: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[BIOMETRIC_ENABLED] ?: false
        }
    
    suspend fun saveEmployeeInfo(
        id: String,
        name: String,
        email: String,
        position: String,
        department: String,
        profileImage: String? = null
    ) {
        dataStore.edit { preferences ->
            preferences[EMPLOYEE_ID] = id
            preferences[EMPLOYEE_NAME] = name
            preferences[EMPLOYEE_EMAIL] = email
            preferences[EMPLOYEE_POSITION] = position
            preferences[EMPLOYEE_DEPARTMENT] = department
            profileImage?.let { preferences[EMPLOYEE_PROFILE_IMAGE] = it }
            preferences[IS_LOGGED_IN] = true
        }
    }
    
    suspend fun setBiometricEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = enabled
        }
    }
    
    suspend fun setPinCode(pin: String) {
        dataStore.edit { preferences ->
            preferences[PIN_CODE] = pin
        }
    }
    
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    suspend fun updateProfileImage(imageUrl: String) {
        dataStore.edit { preferences ->
            preferences[EMPLOYEE_PROFILE_IMAGE] = imageUrl
        }
    }
}
