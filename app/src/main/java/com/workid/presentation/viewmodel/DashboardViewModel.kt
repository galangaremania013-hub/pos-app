package com.workid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.Employee
import com.workid.domain.usecase.GetCurrentEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState
    
    init {
        loadEmployeeData()
    }
    
    private fun loadEmployeeData() {
        viewModelScope.launch {
            getCurrentEmployeeUseCase()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
                .collect { employee ->
                    _uiState.value = _uiState.value.copy(
                        employee = employee,
                        isLoading = false,
                        greeting = getGreeting(employee?.name ?: "Karyawan")
                    )
                }
        }
    }
    
    private fun getGreeting(name: String): String {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when {
            hour < 12 -> "Selamat Pagi, $name"
            hour < 15 -> "Selamat Siang, $name"
            hour < 18 -> "Selamat Sore, $name"
            else -> "Selamat Malam, $name"
        }
    }
}

data class DashboardUiState(
    val employee: Employee? = null,
    val greeting: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)
