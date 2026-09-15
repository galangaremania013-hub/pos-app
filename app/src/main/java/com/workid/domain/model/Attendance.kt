package com.workid.domain.model

data class Attendance(
    val id: String,
    val employeeId: String,
    val date: Long,
    val checkInTime: Long?,
    val checkOutTime: Long?,
    val checkInLocation: LocationData?,
    val checkOutLocation: LocationData?,
    val status: AttendanceStatus,
    val notes: String? = null
)

enum class AttendanceStatus {
    ON_TIME,
    LATE,
    ABSENT,
    HALF_DAY,
    PENDING
}

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null
)
