package com.workid.domain.model

data class Employee(
    val id: String,
    val name: String,
    val email: String,
    val position: String,
    val department: String,
    val profileImageUrl: String?,
    val joinDate: Long,
    val employeeCode: String
)
