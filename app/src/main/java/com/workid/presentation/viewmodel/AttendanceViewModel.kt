package com.workid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.*
import com.workid.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val getTodayAttendanceUseCase: GetTodayAttendanceUseCase,
    private val checkInUseCase: CheckInUseCase,
    private val checkOutUseCase: CheckOutUseCase,
    private val validateLocationUseCase: ValidateLocationUseCase,
    private val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState
    
    init {
        loadEmployeeData()
    }
    
    private fun loadEmployeeData() {
        viewModelScope.launch {
            getCurrentEmployeeUseCase()
                .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                .collect { employee ->
                    _uiState.value = _uiState.value.copy(employeeId = employee?.id)
                    employee?.id?.let { loadTodayAttendance(it) }
                }
        }
    }
    
    private fun loadTodayAttendance(employeeId: String) {
        viewModelScope.launch {
            val today = System.currentTimeMillis()
            getTodayAttendanceUseCase(employeeId, today)
                .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                .collect { attendance ->
                    _uiState.value = _uiState.value.copy(
                        todayAttendance = attendance,
                        isCheckedIn = attendance?.checkInTime != null,
                        isCheckedOut = attendance?.checkOutTime != null,
                        isLoading = false
                    )
                }
        }
    }
    
    fun performCheckIn(latitude: Double, longitude: Double, address: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val location = LocationData(latitude, longitude, address)
            val timestamp = System.currentTimeMillis()
            
            val result = checkInUseCase(_uiState.value.employeeId ?: "", location, timestamp)
            
            result.onSuccess { attendance ->
                _uiState.value = _uiState.value.copy(
                    todayAttendance = attendance,
                    isCheckedIn = true,
                    isLoading = false,
                    scanSuccess = true
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false,
                    scanSuccess = false
                )
            }
        }
    }
    
    fun performCheckOut(latitude: Double, longitude: Double, address: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val location = LocationData(latitude, longitude, address)
            val timestamp = System.currentTimeMillis()
            
            val result = checkOutUseCase(_uiState.value.employeeId ?: "", location, timestamp)
            
            result.onSuccess { attendance ->
                _uiState.value = _uiState.value.copy(
                    todayAttendance = attendance,
                    isCheckedOut = true,
                    isLoading = false,
                    scanSuccess = true
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false,
                    scanSuccess = false
                )
            }
        }
    }
    
    fun resetScanSuccess() {
        _uiState.value = _uiState.value.copy(scanSuccess = false)
    }
}

data class AttendanceUiState(
    val employeeId: String? = null,
    val todayAttendance: Attendance? = null,
    val isCheckedIn: Boolean = false,
    val isCheckedOut: Boolean = false,
    val isLoading: Boolean = true,
    val scanSuccess: Boolean = false,
    val error: String? = null
)
