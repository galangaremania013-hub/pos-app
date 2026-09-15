package com.workid.domain.repository

import com.workid.domain.model.Attendance
import com.workid.domain.model.Employee
import com.workid.domain.model.LocationData
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    fun getCurrentEmployeeAttendance(employeeId: String, date: Long): Flow<Attendance?>
    
    suspend fun checkIn(employeeId: String, location: LocationData, timestamp: Long): Result<Attendance>
    
    suspend fun checkOut(employeeId: String, location: LocationData, timestamp: Long): Result<Attendance>
    
    fun getAttendanceHistory(employeeId: String, month: Int, year: Int): Flow<List<Attendance>>
    
    suspend fun validateLocation(location: LocationData): Result<Boolean>
}
