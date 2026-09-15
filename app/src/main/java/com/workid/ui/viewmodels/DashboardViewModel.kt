package com.workid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.*
import com.workid.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val employee: Employee? = null,
    val todayAttendance: AttendanceRecord? = null,
    val leaveBalance: LeaveBalance? = null,
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

sealed class DashboardEvent {
    object NavigateToAttendance : DashboardEvent()
    object NavigateToLeave : DashboardEvent()
    object NavigateToPayroll : DashboardEvent()
    object NavigateToOvertime : DashboardEvent()
    data class ShowError(val message: String) : DashboardEvent()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentEmployee: GetCurrentEmployee,
    private val getEmployeeAttendance: GetEmployeeAttendance,
    private val getLeaveBalance: GetLeaveBalance,
    private val getAnnouncements: GetAnnouncements
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    private val _events = MutableSharedFlow<DashboardEvent>()
    val events: SharedFlow<DashboardEvent> = _events.asSharedFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                combine(
                    getCurrentEmployee(),
                    getEmployeeAttendance(),
                    getLeaveBalance("EMP001"),
                    getAnnouncements()
                ) { employee, attendance, leaveBalance, announcements ->
                    DashboardUiState(
                        employee = employee,
                        todayAttendance = attendance,
                        leaveBalance = leaveBalance,
                        announcements = announcements.filter { it.isActive }.take(3),
                        isLoading = false,
                        error = null
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
    
    fun onQuickActionClick(action: QuickAction) {
        viewModelScope.launch {
            when (action) {
                QuickAction.SCAN_QR -> _events.emit(DashboardEvent.NavigateToAttendance)
                QuickAction.LEAVE -> _events.emit(DashboardEvent.NavigateToLeave)
                QuickAction.PAYSLIP -> _events.emit(DashboardEvent.NavigateToPayroll)
                QuickAction.OVERTIME -> _events.emit(DashboardEvent.NavigateToOvertime)
            }
        }
    }
    
    fun showError(message: String) {
        viewModelScope.launch {
            _events.emit(DashboardEvent.ShowError(message))
        }
    }
}

enum class QuickAction {
    SCAN_QR,
    LEAVE,
    PAYSLIP,
    OVERTIME
}
