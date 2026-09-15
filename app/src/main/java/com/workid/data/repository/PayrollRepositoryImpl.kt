package com.workid.data.repository

import com.workid.domain.model.*
import com.workid.domain.repository.PayrollRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayrollRepositoryImpl @Inject constructor() : PayrollRepository {
    
    private val mockPayrolls = listOf(
        Payroll(
            id = "payroll_2024_01",
            employeeId = "emp_001",
            period = "2024-01",
            periodStart = System.currentTimeMillis() - 86400000 * 60,
            periodEnd = System.currentTimeMillis(),
            payDate = System.currentTimeMillis(),
            salaryComponents = SalaryComponents(
                basicSalary = 15000000.0,
                allowances = listOf(
                    Allowance("Tunjangan Transport", 1500000.0, AllowanceType.TRANSPORT),
                    Allowance("Tunjangan Makan", 1000000.0, AllowanceType.MEAL)
                ),
                overtime = OvertimePay(10.0, 100000.0, 1000000.0),
                otherIncome = 500000.0
            ),
            deductions = Deductions(
                tax = 750000.0,
                bpjsHealth = 150000.0,
                bpjsEmployment = 100000.0
            ),
            netSalary = 18100000.0,
            status = PayrollStatus.PAID,
            isViewed = false
        )
    )
    
    override fun getPayrollHistory(employeeId: String): Flow<List<Payroll>> = flow {
        kotlinx.coroutines.delay(300)
        emit(mockPayrolls.filter { it.employeeId == employeeId })
    }
    
    override suspend fun getPayrollById(payrollId: String): Result<Payroll> {
        return Result.success(
            mockPayrolls.firstOrNull { it.id == payrollId }
                ?: throw Exception("Payroll not found")
        )
    }
    
    override suspend fun getCurrentPayroll(employeeId: String): Result<Payroll?> {
        return Result.success(mockPayrolls.firstOrNull { it.employeeId == employeeId })
    }
    
    override suspend fun exportPayrollToPdf(payrollId: String): Result<String> {
        return try {
            // In real implementation, generate PDF and return file path
            Result.success("/storage/emulated/0/Download/slip_gaji_$payrollId.pdf")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun markAsViewed(payrollId: String): Result<Unit> {
        return Result.success(Unit)
    }
}
