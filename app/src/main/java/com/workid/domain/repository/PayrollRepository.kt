package com.workid.domain.repository

import com.workid.domain.model.Payroll
import kotlinx.coroutines.flow.Flow

interface PayrollRepository {
    fun getPayrollHistory(employeeId: String): Flow<List<Payroll>>
    
    suspend fun getPayrollById(payrollId: String): Result<Payroll>
    
    suspend fun getCurrentPayroll(employeeId: String): Result<Payroll?>
    
    suspend fun exportPayrollToPdf(payrollId: String): Result<String> // Returns file path
    
    suspend fun markAsViewed(payrollId: String): Result<Unit>
}
