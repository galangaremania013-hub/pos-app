package com.workid.domain.model

data class Employee(
    val id: String,
    val name: String,
    val email: String,
    val position: String,
    val department: String,
    val profileImageUrl: String?,
    val joinDate: String,
    val employeeId: String
)

data class AttendanceRecord(
    val id: String,
    val employeeId: String,
    val date: String,
    val checkInTime: String?,
    val checkOutTime: String?,
    val checkInLocation: Location?,
    val checkOutLocation: Location?,
    val status: AttendanceStatus,
    val notes: String?
)

data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String?
)

enum class AttendanceStatus {
    PRESENT,
    LATE,
    ABSENT,
    PENDING,
    ON_LEAVE
}

data class LeaveRequest(
    val id: String,
    val employeeId: String,
    val leaveType: LeaveType,
    val startDate: String,
    val endDate: String,
    val reason: String,
    val attachmentUrl: String?,
    val status: LeaveStatus,
    val submittedDate: String,
    val approvedBy: String?,
    val approvedDate: String?,
    val rejectionReason: String?
)

enum class LeaveType {
    ANNUAL,
    SICK,
    PERSONAL,
    MATERNITY,
    PATERNITY,
    UNPAID
}

enum class LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED
}

data class LeaveBalance(
    val employeeId: String,
    val year: Int,
    val totalQuota: Int,
    val used: Int,
    val remaining: Int,
    val expireDate: String
)

data class Payslip(
    val id: String,
    val employeeId: String,
    val period: String,
    val issueDate: String,
    val basicSalary: Double,
    val allowances: List<Allowance>,
    val overtime: Overtime?,
    val deductions: List<Deduction>,
    val grossSalary: Double,
    val netSalary: Double,
    val paymentStatus: PaymentStatus,
    val paymentDate: String?
)

data class Allowance(
    val type: String,
    val amount: Double,
    val description: String?
)

data class Overtime(
    val hours: Double,
    val rate: Double,
    val total: Double,
    val approvalStatus: ApprovalStatus
)

data class Deduction(
    val type: String,
    val amount: Double,
    val description: String?
)

enum class PaymentStatus {
    PAID,
    PENDING,
    PROCESSING
}

enum class ApprovalStatus {
    PENDING,
    APPROVED,
    REJECTED
}

data class OvertimeClaim(
    val id: String,
    val employeeId: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val totalHours: Double,
    val reason: String,
    val status: ApprovalStatus,
    val submittedDate: String,
    val approvedBy: String?,
    val notes: String?
)

data class Announcement(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String?,
    val publishedDate: String,
    val priority: Priority,
    val isActive: Boolean,
    val targetAudience: TargetAudience
)

enum class Priority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

enum class TargetAudience {
    ALL,
    MANAGEMENT,
    STAFF,
    SPECIFIC_DEPARTMENT
}

data class DailyTaskReport(
    val id: String,
    val employeeId: String,
    val date: String,
    val tasks: List<TaskItem>,
    val accomplishments: String,
    val challenges: String?,
    val plans: String?,
    val submittedDate: String
)

data class TaskItem(
    val description: String,
    val status: TaskStatus,
    val completedAt: String?
)

enum class TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE,
    BLOCKED
}

data class QRCodeResult(
    val qrData: String,
    val timestamp: Long,
    val isValid: Boolean,
    val employeeId: String?
)
