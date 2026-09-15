package com.workid.domain.repository

import com.workid.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getCurrentEmployeeAttendance(): Flow<AttendanceRecord?>
    suspend fun submitAttendance(record: AttendanceRecord): Result<Unit>
    suspend fun getAttendanceHistory(employeeId: String, month: Int, year: Int): List<AttendanceRecord>
    suspend fun validateLocation(location: Location): Boolean
}

interface LeaveRepository {
    fun getLeaveBalance(employeeId: String): Flow<LeaveBalance?>
    suspend fun submitLeaveRequest(request: LeaveRequest): Result<String>
    suspend fun getLeaveRequests(employeeId: String): List<LeaveRequest>
    suspend fun cancelLeaveRequest(requestId: String): Result<Unit>
}

interface PayslipRepository {
    suspend fun getPayslips(employeeId: String): List<Payslip>
    suspend fun getPayslipById(id: String): Payslip?
    suspend fun generatePayslipPDF(payslip: Payslip): Result<ByteArray>
}

interface OvertimeRepository {
    suspend fun submitOvertimeClaim(claim: OvertimeClaim): Result<String>
    suspend fun getOvertimeClaims(employeeId: String): List<OvertimeClaim>
    suspend fun approveOvertimeClaim(claimId: String, approvedBy: String): Result<Unit>
}

interface AnnouncementRepository {
    fun getAnnouncements(): Flow<List<Announcement>>
    suspend fun getActiveAnnouncements(): List<Announcement>
}

interface TaskRepository {
    suspend fun submitDailyReport(report: DailyTaskReport): Result<String>
    suspend fun getDailyReports(employeeId: String, month: Int, year: Int): List<DailyTaskReport>
}

interface EmployeeRepository {
    fun getCurrentEmployee(): Flow<Employee?>
    suspend fun getEmployeeById(id: String): Employee?
    suspend fun updateProfile(employee: Employee): Result<Unit>
}

interface QRCodeRepository {
    suspend fun validateQRCode(qrData: String): QRCodeResult
}
