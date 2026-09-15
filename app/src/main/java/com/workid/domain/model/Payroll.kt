package com.workid.domain.model

data class Payroll(
    val id: String,
    val employeeId: String,
    val period: String, // e.g., "2024-01"
    val periodStart: Long,
    val periodEnd: Long,
    val payDate: Long,
    val salaryComponents: SalaryComponents,
    val deductions: Deductions,
    val netSalary: Double,
    val status: PayrollStatus,
    val isViewed: Boolean = false
)

data class SalaryComponents(
    val basicSalary: Double,
    val allowances: List<Allowance>,
    val overtime: OvertimePay,
    val otherIncome: Double = 0.0
) {
    val totalGross: Double
        get() = basicSalary + allowances.sumOf { it.amount } + overtime.totalAmount + otherIncome
}

data class Allowance(
    val name: String,
    val amount: Double,
    val type: AllowanceType
)

enum class AllowanceType {
    TRANSPORT,
    MEAL,
    HOUSING,
    POSITION,
    PERFORMANCE,
    OTHER
}

data class OvertimePay(
    val totalHours: Double,
    val ratePerHour: Double,
    val totalAmount: Double
)

data class Deductions(
    val tax: Double, // PPh21
    val bpjsHealth: Double,
    val bpjsEmployment: Double,
    val otherDeductions: Double = 0.0
) {
    val totalDeductions: Double
        get() = tax + bpjsHealth + bpjsEmployment + otherDeductions
}

enum class PayrollStatus {
    PAID,
    PENDING,
    FAILED,
    PROCESSING
}
