package com.workid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.*
import com.workid.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AttendanceUiState(
    val todayAttendance: AttendanceRecord? = null,
    val isScanning: Boolean = false,
    val scanSuccess: Boolean = false,
    val locationValid: Boolean = false,
    val currentLocation: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class AttendanceEvent {
    object ScanSuccess : AttendanceEvent()
    object ScanFailed : AttendanceEvent()
    data class ShowError(val message: String) : AttendanceEvent()
    object NavigateBack : AttendanceEvent()
}

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val getEmployeeAttendance: GetEmployeeAttendance,
    private val submitAttendance: SubmitAttendance,
    private val validateLocation: ValidateLocation,
    private val validateQRCode: ValidateQRCode
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AttendanceUiState())
    val uiState: StateFlow<AttendanceUiState> = _uiState.asStateFlow()
    
    private val _events = MutableSharedFlow<AttendanceEvent>()
    val events: SharedFlow<AttendanceEvent> = _events.asSharedFlow()
    
    init {
        loadTodayAttendance()
    }
    
    private fun loadTodayAttendance() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getEmployeeAttendance().collect { attendance ->
                _uiState.value = _uiState.value.copy(
                    todayAttendance = attendance,
                    isLoading = false
                )
            }
        }
    }
    
    fun startScanning() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isScanning = true)
        }
    }
    
    fun stopScanning() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isScanning = false)
        }
    }
    
    fun onQRCodeScanned(qrData: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Validate QR Code
                val qrResult = validateQRCode(qrData)
                
                if (!qrResult.isValid) {
                    _uiState.value = _uiState.value.copy(
                        isScanning = false,
                        isLoading = false,
                        scanSuccess = false
                    )
                    _events.emit(AttendanceEvent.ScanFailed)
                    return@launch
                }
                
                // Get current location
                val currentLocation = getCurrentLocation() // Mock - implement with actual GPS
                
                // Validate location
                val isLocationValid = currentLocation?.let { validateLocation(it) } ?: false
                
                if (!isLocationValid) {
                    _uiState.value = _uiState.value.copy(
                        isScanning = false,
                        isLoading = false,
                        locationValid = false
                    )
                    _events.emit(AttendanceEvent.ShowError("Anda berada di luar jangkauan kantor"))
                    return@launch
                }
                
                // Submit attendance
                val now = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                    .format(java.util.Date())
                
                val attendanceRecord = AttendanceRecord(
                    id = "ATT-${System.currentTimeMillis()}",
                    employeeId = qrResult.employeeId ?: "EMP001",
                    date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        .format(java.util.Date()),
                    checkInTime = now,
                    checkOutTime = null,
                    checkInLocation = currentLocation,
                    checkOutLocation = null,
                    status = AttendanceStatus.PRESENT,
                    notes = null
                )
                
                val result = submitAttendance(attendanceRecord)
                
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isScanning = false,
                        isLoading = false,
                        scanSuccess = true,
                        todayAttendance = attendanceRecord
                    )
                    _events.emit(AttendanceEvent.ScanSuccess)
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isScanning = false,
                        isLoading = false,
                        scanSuccess = false
                    )
                    _events.emit(AttendanceEvent.ScanFailed)
                }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isScanning = false,
                    isLoading = false,
                    error = e.message
                )
                _events.emit(AttendanceEvent.ShowError(e.message ?: "Terjadi kesalahan"))
            }
        }
    }
    
    private suspend fun getCurrentLocation(): Location? {
        // Mock implementation - replace with actual GPS location
        // In real app, use FusedLocationProviderClient
        return Location(-6.2088, 106.8456, "Jakarta Office")
    }
    
    fun resetScanState() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(scanSuccess = false)
        }
    }
}
