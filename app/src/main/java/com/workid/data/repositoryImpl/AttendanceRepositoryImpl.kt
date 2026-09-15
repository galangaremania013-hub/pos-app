package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class AttendanceRepositoryImpl @Inject constructor() : AttendanceRepository {
    
    // Mock data for development
    private val mockAttendanceRecords = mutableListOf<AttendanceRecord>()
    
    override fun getCurrentEmployeeAttendance(): Flow<AttendanceRecord?> = flow {
        // Simulate API call delay
        kotlinx.coroutines.delay(500)
        
        // Return mock today's attendance
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            .format(java.util.Date())
        
        val mockRecord = AttendanceRecord(
            id = "ATT-${System.currentTimeMillis()}",
            employeeId = "EMP001",
            date = today,
            checkInTime = "08:45",
            checkOutTime = null,
            checkInLocation = Location(-6.2088, 106.8456, "Jakarta Office"),
            checkOutLocation = null,
            status = AttendanceStatus.PRESENT,
            notes = null
        )
        
        emit(mockRecord)
    }
    
    override suspend fun submitAttendance(record: AttendanceRecord): Result<Unit> {
        // Simulate API submission
        return try {
            kotlinx.coroutines.delay(1000)
            mockAttendanceRecords.add(record)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getAttendanceHistory(
        employeeId: String,
        month: Int,
        year: Int
    ): List<AttendanceRecord> {
        // Generate mock history data
        return generateMockAttendanceHistory(employeeId, month, year)
    }
    
    override suspend fun validateLocation(location: Location): Boolean {
        // Mock office location validation (Jakarta coordinates as example)
        val officeLat = -6.2088
        val officeLng = 106.8456
        val maxDistanceKm = 0.5 // 500 meters radius
        
        val distance = calculateDistance(
            officeLat, officeLng,
            location.latitude, location.longitude
        )
        
        return distance <= maxDistanceKm
    }
    
    private fun generateMockAttendanceHistory(
        employeeId: String,
        month: Int,
        year: Int
    ): List<AttendanceRecord> {
        val records = mutableListOf<AttendanceRecord>()
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        
        val daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
        
        for (day in 1..daysInMonth) {
            calendar.set(java.util.Calendar.DAY_OF_MONTH, day)
            val date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(calendar.time)
            
            // Skip weekends
            val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            if (dayOfWeek == java.util.Calendar.SUNDAY || dayOfWeek == java.util.Calendar.SATURDAY) {
                continue
            }
            
            val checkInHour = 8 + Random.nextInt(0, 2) // 8-9 AM
            val checkInMinute = Random.nextInt(0, 60)
            val isLate = checkInHour > 8 || (checkInHour == 8 && checkInMinute > 30)
            
            records.add(
                AttendanceRecord(
                    id = "ATT-$day",
                    employeeId = employeeId,
                    date = date,
                    checkInTime = String.format("%02d:%02d", checkInHour, checkInMinute),
                    checkOutTime = String.format("%02d:%02d", 17 + Random.nextInt(0, 3), Random.nextInt(0, 60)),
                    checkInLocation = Location(-6.2088, 106.8456, "Jakarta Office"),
                    checkOutLocation = Location(-6.2088, 106.8456, "Jakarta Office"),
                    status = if (isLate) AttendanceStatus.LATE else AttendanceStatus.PRESENT,
                    notes = if (isLate) "Late arrival" else null
                )
            )
        }
        
        return records
    }
    
    private fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val earthRadiusKm = 6371.0
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        
        return earthRadiusKm * c
    }
}
