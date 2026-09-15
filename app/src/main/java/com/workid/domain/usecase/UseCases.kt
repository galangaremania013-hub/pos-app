package com.workid.domain.usecase

import com.workid.domain.model.*
import com.workid.domain.repository.*
import kotlinx.coroutines.flow.Flow

// Attendance Use Cases
class GetEmployeeAttendance(private val repository: AttendanceRepository) {
    operator fun invoke(): Flow<AttendanceRecord?> = repository.getCurrentEmployeeAttendance()
}

class SubmitAttendance(private val repository: AttendanceRepository) {
    suspend operator fun invoke(record: AttendanceRecord): Result<Unit> = 
        repository.submitAttendance(record)
}

class GetAttendanceHistory(private val repository: AttendanceRepository) {
    suspend operator fun invoke(employeeId: String, month: Int, year: Int): List<AttendanceRecord> =
        repository.getAttendanceHistory(employeeId, month, year)
}

class ValidateLocation(private val repository: AttendanceRepository) {
    suspend operator fun invoke(location: Location): Boolean = 
        repository.validateLocation(location)
}

// Leave Use Cases
class GetLeaveBalance(private val repository: LeaveRepository) {
    operator fun invoke(employeeId: String): Flow<LeaveBalance?> = 
        repository.getLeaveBalance(employeeId)
}

class SubmitLeaveRequest(private val repository: LeaveRepository) {
    suspend operator fun invoke(request: LeaveRequest): Result<String> =
        repository.submitLeaveRequest(request)
}

class GetLeaveRequests(private val repository: LeaveRepository) {
    suspend operator fun invoke(employeeId: String): List<LeaveRequest> =
        repository.getLeaveRequests(employeeId)
}

// Payslip Use Cases
class GetPayslips(private val repository: PayslipRepository) {
    suspend operator fun invoke(employeeId: String): List<Payslip> =
        repository.getPayslips(employeeId)
}

class GeneratePayslipPDF(private val repository: PayslipRepository) {
    suspend operator fun invoke(payslip: Payslip): Result<ByteArray> =
        repository.generatePayslipPDF(payslip)
}

// Overtime Use Cases
class SubmitOvertimeClaim(private val repository: OvertimeRepository) {
    suspend operator fun invoke(claim: OvertimeClaim): Result<String> =
        repository.submitOvertimeClaim(claim)
}

class GetOvertimeClaims(private val repository: OvertimeRepository) {
    suspend operator fun invoke(employeeId: String): List<OvertimeClaim> =
        repository.getOvertimeClaims(employeeId)
}

// Announcement Use Cases
class GetAnnouncements(private val repository: AnnouncementRepository) {
    operator fun invoke(): Flow<List<Announcement>> = repository.getAnnouncements()
}

class GetActiveAnnouncements(private val repository: AnnouncementRepository) {
    suspend operator fun invoke(): List<Announcement> = repository.getActiveAnnouncements()
}

// Task Use Cases
class SubmitDailyReport(private val repository: TaskRepository) {
    suspend operator fun invoke(report: DailyTaskReport): Result<String> =
        repository.submitDailyReport(report)
}

// Employee Use Cases
class GetCurrentEmployee(private val repository: EmployeeRepository) {
    operator fun invoke(): Flow<Employee?> = repository.getCurrentEmployee()
}

class UpdateProfile(private val repository: EmployeeRepository) {
    suspend operator fun invoke(employee: Employee): Result<Unit> =
        repository.updateProfile(employee)
}

// QR Code Use Cases
class ValidateQRCode(private val repository: QRCodeRepository) {
    suspend operator fun invoke(qrData: String): QRCodeResult =
        repository.validateQRCode(qrData)
}
