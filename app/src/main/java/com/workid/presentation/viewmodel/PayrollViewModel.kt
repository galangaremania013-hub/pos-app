package com.workid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.Payroll
import com.workid.domain.usecase.ExportPayrollPdfUseCase
import com.workid.domain.usecase.GetCurrentPayrollUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayrollViewModel @Inject constructor(
    private val getCurrentPayrollUseCase: GetCurrentPayrollUseCase,
    private val exportPayrollPdfUseCase: ExportPayrollPdfUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PayrollUiState())
    val uiState: StateFlow<PayrollUiState> = _uiState
    
    fun loadCurrentPayroll(employeeId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getCurrentPayrollUseCase(employeeId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
                .collect { payroll ->
                    _uiState.value = _uiState.value.copy(
                        currentPayroll = payroll,
                        isLoading = false,
                        isAuthenticated = payroll != null
                    )
                }
        }
    }
    
    fun exportToPdf(payrollId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isExporting = true)
            
            exportPayrollPdfUseCase(payrollId)
                .onSuccess { filePath ->
                    _uiState.value = _uiState.value.copy(
                        pdfFilePath = filePath,
                        isExporting = false
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isExporting = false
                    )
                }
        }
    }
    
    fun setAuthenticated(authenticated: Boolean) {
        _uiState.value = _uiState.value.copy(isAuthenticated = authenticated)
    }
}

data class PayrollUiState(
    val currentPayroll: Payroll? = null,
    val isLoading: Boolean = true,
    val isAuthenticated: Boolean = false,
    val isExporting: Boolean = false,
    val pdfFilePath: String? = null,
    val error: String? = null
)
