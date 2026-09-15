package com.workid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.LeaveBalance
import com.workid.domain.model.LeaveRequest
import com.workid.domain.usecase.GetLeaveBalanceUseCase
import com.workid.domain.usecase.GetLeaveRequestsUseCase
import com.workid.domain.usecase.SubmitLeaveRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaveViewModel @Inject constructor(
    private val getLeaveBalanceUseCase: GetLeaveBalanceUseCase,
    private val getLeaveRequestsUseCase: GetLeaveRequestsUseCase,
    private val submitLeaveRequestUseCase: SubmitLeaveRequestUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LeaveUiState())
    val uiState: StateFlow<LeaveUiState> = _uiState
    
    fun loadLeaveData(employeeId: String) {
        viewModelScope.launch {
            launch {
                getLeaveBalanceUseCase(employeeId)
                    .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                    .collect { balance ->
                        _uiState.value = _uiState.value.copy(
                            leaveBalance = balance,
                            isLoading = false
                        )
                    }
            }
            launch {
                getLeaveRequestsUseCase(employeeId)
                    .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                    .collect { requests ->
                        _uiState.value = _uiState.value.copy(leaveRequests = requests)
                    }
            }
        }
    }
    
    fun submitLeaveRequest(leaveRequest: LeaveRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitting = true)
            
            submitLeaveRequestUseCase(leaveRequest)
                .onSuccess { id ->
                    _uiState.value = _uiState.value.copy(
                        isSubmitting = false,
                        submissionSuccess = true
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isSubmitting = false,
                        submissionSuccess = false
                    )
                }
        }
    }
    
    fun resetSubmissionState() {
        _uiState.value = _uiState.value.copy(submissionSuccess = false)
    }
}

data class LeaveUiState(
    val leaveBalance: LeaveBalance? = null,
    val leaveRequests: List<LeaveRequest> = emptyList(),
    val isLoading: Boolean = true,
    val isSubmitting: Boolean = false,
    val submissionSuccess: Boolean = false,
    val error: String? = null
)
