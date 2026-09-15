package com.workid.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workid.domain.model.*
import com.workid.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

data class PayrollUiState(
    val payslips: List<Payslip> = emptyList(),
    val selectedPayslip: Payslip? = null,
    val isLoading: Boolean = true,
    val isGeneratingPDF: Boolean = false,
    val biometricRequired: Boolean = true,
    val biometricAuthenticated: Boolean = false,
    val error: String? = null
)

sealed class PayrollEvent {
    object ShowBiometricPrompt : PayrollEvent()
    object BiometricSuccess : PayrollEvent()
    object BiometricFailed : PayrollEvent()
    object PDFGenerated : PayrollEvent()
    data class ShowError(val message: String) : PayrollEvent()
}

@HiltViewModel
class PayrollViewModel @Inject constructor(
    private val getPayslips: GetPayslips,
    private val generatePayslipPDF: GeneratePayslipPDF
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PayrollUiState())
    val uiState: StateFlow<PayrollUiState> = _uiState.asStateFlow()
    
    private val _events = MutableSharedFlow<PayrollEvent>()
    val events: SharedFlow<PayrollEvent> = _events.asSharedFlow()
    
    init {
        loadPayslips()
    }
    
    private fun loadPayslips() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val payslips = getPayslips("EMP001")
                _uiState.value = _uiState.value.copy(
                    payslips = payslips.sortedByDescending { it.period },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun onSelectPayslip(payslip: Payslip) {
        if (_uiState.value.biometricAuthenticated) {
            _uiState.value = _uiState.value.copy(selectedPayslip = payslip)
        } else {
            viewModelScope.launch {
                _events.emit(PayrollEvent.ShowBiometricPrompt)
            }
        }
    }
    
    fun onBiometricSuccess() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                biometricAuthenticated = true,
                biometricRequired = false
            )
            _events.emit(PayrollEvent.BiometricSuccess)
        }
    }
    
    fun onBiometricFailed() {
        viewModelScope.launch {
            _events.emit(PayrollEvent.BiometricFailed)
        }
    }
    
    fun onExportPDF(payslip: Payslip) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGeneratingPDF = true)
            
            try {
                val result = generatePayslipPDF(payslip)
                
                result.onSuccess { pdfBytes ->
                    _uiState.value = _uiState.value.copy(isGeneratingPDF = false)
                    _events.emit(PayrollEvent.PDFGenerated)
                    // Save PDF to device - implement file saving logic here
                }.onFailure {
                    _uiState.value = _uiState.value.copy(isGeneratingPDF = false)
                    _events.emit(PayrollEvent.ShowError("Gagal menghasilkan PDF"))
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isGeneratingPDF = false)
                _events.emit(PayrollEvent.ShowError(e.message ?: "Terjadi kesalahan"))
            }
        }
    }
    
    fun formatCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount)
    }
    
    fun resetAuthentication() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                biometricAuthenticated = false,
                biometricRequired = true,
                selectedPayslip = null
            )
        }
    }
}
