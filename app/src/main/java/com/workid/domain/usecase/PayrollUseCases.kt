package com.workid.domain.usecase

import com.workid.domain.model.Payroll
import com.workid.domain.repository.PayrollRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPayrollHistoryUseCase @Inject constructor(
    private val payrollRepository: PayrollRepository
) {
    operator fun invoke(employeeId: String): Flow<List<Payroll>> {
        return payrollRepository.getPayrollHistory(employeeId)
    }
}

class GetCurrentPayrollUseCase @Inject constructor(
    private val payrollRepository: PayrollRepository
) {
    suspend operator fun invoke(employeeId: String): Result<Payroll?> {
        return payrollRepository.getCurrentPayroll(employeeId)
    }
}

class ExportPayrollPdfUseCase @Inject constructor(
    private val payrollRepository: PayrollRepository
) {
    suspend operator fun invoke(payrollId: String): Result<String> {
        return payrollRepository.exportPayrollToPdf(payrollId)
    }
}
