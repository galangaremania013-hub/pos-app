package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.PayslipRepository
import javax.inject.Inject

class PayslipRepositoryImpl @Inject constructor() : PayslipRepository {
    
    override suspend fun getPayslips(employeeId: String): List<Payslip> {
        return listOf(
            createMockPayslip("2024-03", 15000000.0, 2500000.0, 1500000.0, 750000.0),
            createMockPayslip("2024-02", 15000000.0, 2000000.0, 2000000.0, 800000.0),
            createMockPayslip("2024-01", 15000000.0, 2500000.0, 1000000.0, 700000.0)
        )
    }
    
    override suspend fun getPayslipById(id: String): Payslip? {
        return createMockPayslip("2024-03", 15000000.0, 2500000.0, 1500000.0, 750000.0).copy(id = id)
    }
    
    override suspend fun generatePayslipPDF(payslip: Payslip): Result<ByteArray> {
        return try {
            kotlinx.coroutines.delay(1500)
            // Mock PDF generation - in real implementation, use iText PDF library
            val mockPdfBytes = ByteArray(1024) { 0 }
            Result.success(mockPdfBytes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun createMockPayslip(
        period: String,
        basicSalary: Double,
        totalAllowances: Double,
        overtimeAmount: Double,
        totalDeductions: Double
    ): Payslip {
        val grossSalary = basicSalary + totalAllowances + overtimeAmount
        val netSalary = grossSalary - totalDeductions
        
        return Payslip(
            id = "PAY-${period.replace("-", "")}",
            employeeId = "EMP001",
            period = period,
            issueDate = "${period}-25",
            basicSalary = basicSalary,
            allowances = listOf(
                Allowance("Transport", 1000000.0, "Monthly transport allowance"),
                Allowance("Meal", 1500000.0, "Daily meal allowance")
            ),
            overtime = Overtime(
                hours = if (overtimeAmount > 0) overtimeAmount / 50000 else 0.0,
                rate = 50000.0,
                total = overtimeAmount,
                approvalStatus = ApprovalStatus.APPROVED
            ),
            deductions = listOf(
                Deduction("PPh21", totalDeductions * 0.7, "Income tax"),
                Deduction("BPJS Kesehatan", totalDeductions * 0.2, "Health insurance"),
                Deduction("BPJS Ketenagakerjaan", totalDeductions * 0.1, "Employment insurance")
            ),
            grossSalary = grossSalary,
            netSalary = netSalary,
            paymentStatus = PaymentStatus.PAID,
            paymentDate = "${period}-28"
        )
    }
}
