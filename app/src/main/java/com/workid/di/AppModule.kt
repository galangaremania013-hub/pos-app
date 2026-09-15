package com.workid.di

import android.content.Context
import com.workid.data.repositoryImpl.*
import com.workid.domain.repository.*
import com.workid.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        impl: AttendanceRepositoryImpl
    ): AttendanceRepository
    
    @Binds
    @Singleton
    abstract fun bindLeaveRepository(
        impl: LeaveRepositoryImpl
    ): LeaveRepository
    
    @Binds
    @Singleton
    abstract fun bindPayslipRepository(
        impl: PayslipRepositoryImpl
    ): PayslipRepository
    
    @Binds
    @Singleton
    abstract fun bindOvertimeRepository(
        impl: OvertimeRepositoryImpl
    ): OvertimeRepository
    
    @Binds
    @Singleton
    abstract fun bindAnnouncementRepository(
        impl: AnnouncementRepositoryImpl
    ): AnnouncementRepository
    
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository
    
    @Binds
    @Singleton
    abstract fun bindEmployeeRepository(
        impl: EmployeeRepositoryImpl
    ): EmployeeRepository
    
    @Binds
    @Singleton
    abstract fun bindQRCodeRepository(
        impl: QRCodeRepositoryImpl
    ): QRCodeRepository
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    
    @Provides
    @Singleton
    fun provideGetEmployeeAttendance(repository: AttendanceRepository): GetEmployeeAttendance =
        GetEmployeeAttendance(repository)
    
    @Provides
    @Singleton
    fun provideSubmitAttendance(repository: AttendanceRepository): SubmitAttendance =
        SubmitAttendance(repository)
    
    @Provides
    @Singleton
    fun provideGetAttendanceHistory(repository: AttendanceRepository): GetAttendanceHistory =
        GetAttendanceHistory(repository)
    
    @Provides
    @Singleton
    fun provideValidateLocation(repository: AttendanceRepository): ValidateLocation =
        ValidateLocation(repository)
    
    @Provides
    @Singleton
    fun provideGetLeaveBalance(repository: LeaveRepository): GetLeaveBalance =
        GetLeaveBalance(repository)
    
    @Provides
    @Singleton
    fun provideSubmitLeaveRequest(repository: LeaveRepository): SubmitLeaveRequest =
        SubmitLeaveRequest(repository)
    
    @Provides
    @Singleton
    fun provideGetLeaveRequests(repository: LeaveRepository): GetLeaveRequests =
        GetLeaveRequests(repository)
    
    @Provides
    @Singleton
    fun provideGetPayslips(repository: PayslipRepository): GetPayslips =
        GetPayslips(repository)
    
    @Provides
    @Singleton
    fun provideGeneratePayslipPDF(repository: PayslipRepository): GeneratePayslipPDF =
        GeneratePayslipPDF(repository)
    
    @Provides
    @Singleton
    fun provideSubmitOvertimeClaim(repository: OvertimeRepository): SubmitOvertimeClaim =
        SubmitOvertimeClaim(repository)
    
    @Provides
    @Singleton
    fun provideGetOvertimeClaims(repository: OvertimeRepository): GetOvertimeClaims =
        GetOvertimeClaims(repository)
    
    @Provides
    @Singleton
    fun provideGetAnnouncements(repository: AnnouncementRepository): GetAnnouncements =
        GetAnnouncements(repository)
    
    @Provides
    @Singleton
    fun provideGetActiveAnnouncements(repository: AnnouncementRepository): GetActiveAnnouncements =
        GetActiveAnnouncements(repository)
    
    @Provides
    @Singleton
    fun provideSubmitDailyReport(repository: TaskRepository): SubmitDailyReport =
        SubmitDailyReport(repository)
    
    @Provides
    @Singleton
    fun provideGetCurrentEmployee(repository: EmployeeRepository): GetCurrentEmployee =
        GetCurrentEmployee(repository)
    
    @Provides
    @Singleton
    fun provideUpdateProfile(repository: EmployeeRepository): UpdateProfile =
        UpdateProfile(repository)
    
    @Provides
    @Singleton
    fun provideValidateQRCode(repository: QRCodeRepository): ValidateQRCode =
        ValidateQRCode(repository)
}

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    
    @Provides
    @Singleton
    fun provideEmployeeDataStore(@ApplicationContext context: Context): EmployeeDataStore =
        EmployeeDataStore(context)
}
