package com.workid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.*
import com.workid.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LeaveUiState(
    val leaveBalance: LeaveBalance? = null,
    val leaveRequests: List<LeaveRequest> = emptyList(),
    val isLoading: Boolean = true,
    val isSubmitting: Boolean = false,
    val selectedLeaveType: LeaveType = LeaveType.ANNUAL,
    val startDate: String = "",
    val endDate: String = "",
    val reason: String = "",
    val error: String? = null
)

sealed class LeaveEvent {
    object SubmissionSuccess : LeaveEvent()
    data class ShowError(val message: String) : LeaveEvent()
    object NavigateBack : LeaveEvent()
}

@HiltViewModel
class LeaveViewModel @Inject constructor(
    private val getLeaveBalance: GetLeaveBalance,
    private val getLeaveRequests: GetLeaveRequests,
    private val submitLeaveRequest: SubmitLeaveRequest
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LeaveUiState())
    val uiState: StateFlow<LeaveUiState> = _uiState.asStateFlow()
    
    private val _events = MutableSharedFlow<LeaveEvent>()
    val events: SharedFlow<LeaveEvent> = _events.asSharedFlow()
    
    init {
        loadLeaveData()
    }
    
    private fun loadLeaveData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                combine(
                    getLeaveBalance("EMP001"),
                    kotlin.coroutines.flow.flow { emit(getLeaveRequests("EMP001")) }
                ) { balance, requests ->
                    LeaveUiState(
                        leaveBalance = balance,
                        leaveRequests = requests.sortedByDescending { it.submittedDate },
                        isLoading = false
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun updateLeaveType(type: LeaveType) {
        _uiState.value = _uiState.value.copy(selectedLeaveType = type)
    }
    
    fun updateStartDate(date: String) {
        _uiState.value = _uiState.value.copy(startDate = date)
    }
    
    fun updateEndDate(date: String) {
        _uiState.value = _uiState.value.copy(endDate = date)
    }
    
    fun updateReason(reason: String) {
        _uiState.value = _uiState.value.copy(reason = reason)
    }
    
    fun submitLeaveRequest() {
        viewModelScope.launch {
            val state = _uiState.value
            
            // Validation
            if (state.startDate.isEmpty() || state.endDate.isEmpty()) {
                _events.emit(LeaveEvent.ShowError("Tanggal mulai dan selesai harus diisi"))
                return@launch
            }
            
            if (state.reason.isEmpty()) {
                _events.emit(LeaveEvent.ShowError("Alasan cuti harus diisi"))
                return@launch
            }
            
            // Check remaining quota
            val remainingQuota = state.leaveBalance?.remaining ?: 0
            if (state.selectedLeaveType == LeaveType.ANNUAL && remainingQuota <= 0) {
                _events.emit(LeaveEvent.ShowError("Sisa kuota cuti tahunan tidak mencukupi"))
                return@launch
            }
            
            _uiState.value = _uiState.value.copy(isSubmitting = true)
            
            try {
                val request = LeaveRequest(
                    id = "LV-${System.currentTimeMillis()}",
                    employeeId = "EMP001",
                    leaveType = state.selectedLeaveType,
                    startDate = state.startDate,
                    endDate = state.endDate,
                    reason = state.reason,
                    attachmentUrl = null,
                    status = LeaveStatus.PENDING,
                    submittedDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        .format(java.util.Date()),
                    approvedBy = null,
                    approvedDate = null,
                    rejectionReason = null
                )
                
                val result = submitLeaveRequest(request)
                
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(isSubmitting = false)
                    _events.emit(LeaveEvent.SubmissionSuccess)
                    loadLeaveData() // Reload data
                }.onFailure {
                    _uiState.value = _uiState.value.copy(isSubmitting = false)
                    _events.emit(LeaveEvent.ShowError("Gagal mengajukan cuti"))
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isSubmitting = false)
                _events.emit(LeaveEvent.ShowError(e.message ?: "Terjadi kesalahan"))
            }
        }
    }
    
    fun resetForm() {
        _uiState.value = _uiState.value.copy(
            selectedLeaveType = LeaveType.ANNUAL,
            startDate = "",
            endDate = "",
            reason = ""
        )
    }
}
