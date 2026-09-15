package com.workid.data.repository

import com.workid.domain.model.*
import com.workid.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor() : AttendanceRepository {
    
    // Mock data for demonstration
    private val mockAttendances = mutableMapOf<String, Attendance>()
    
    override fun getCurrentEmployeeAttendance(employeeId: String, date: Long): Flow<Attendance?> = flow {
        // Simulate API call delay
        kotlinx.coroutines.delay(300)
        emit(mockAttendances["${employeeId}_$date"])
    }
    
    override suspend fun checkIn(
        employeeId: String,
        location: LocationData,
        timestamp: Long
    ): Result<Attendance> {
        return try {
            val attendance = Attendance(
                id = "att_${System.currentTimeMillis()}",
                employeeId = employeeId,
                date = timestamp,
                checkInTime = timestamp,
                checkOutTime = null,
                checkInLocation = location,
                checkOutLocation = null,
                status = AttendanceStatus.ON_TIME
            )
            mockAttendances["${employeeId}_$timestamp"] = attendance
            Result.success(attendance)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun checkOut(
        employeeId: String,
        location: LocationData,
        timestamp: Long
    ): Result<Attendance> {
        return try {
            val existingAttendance = mockAttendances["${employeeId}_$timestamp"]
            if (existingAttendance != null) {
                val updatedAttendance = existingAttendance.copy(
                    checkOutTime = timestamp,
                    checkOutLocation = location
                )
                mockAttendances["${employeeId}_$timestamp"] = updatedAttendance
                Result.success(updatedAttendance)
            } else {
                Result.failure(Exception("No check-in record found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getAttendanceHistory(
        employeeId: String,
        month: Int,
        year: Int
    ): Flow<List<Attendance>> = flow {
        kotlinx.coroutines.delay(500)
        emit(emptyList()) // Return filtered list in real implementation
    }
    
    override suspend fun validateLocation(location: LocationData): Result<Boolean> {
        // Implement geofencing logic here
        // For demo, always return true
        return Result.success(true)
    }
}
