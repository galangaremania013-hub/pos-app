package com.workid.domain.usecase

import com.workid.domain.model.Attendance
import com.workid.domain.model.LocationData
import com.workid.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodayAttendanceUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) {
    operator fun invoke(employeeId: String, timestamp: Long): Flow<Attendance?> {
        return attendanceRepository.getCurrentEmployeeAttendance(employeeId, timestamp)
    }
}

class CheckInUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(employeeId: String, location: LocationData, timestamp: Long): Result<Attendance> {
        return attendanceRepository.checkIn(employeeId, location, timestamp)
    }
}

class CheckOutUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(employeeId: String, location: LocationData, timestamp: Long): Result<Attendance> {
        return attendanceRepository.checkOut(employeeId, location, timestamp)
    }
}

class ValidateLocationUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(location: LocationData): Result<Boolean> {
        return attendanceRepository.validateLocation(location)
    }
}
