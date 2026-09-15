package com.workid.data.repositoryImpl

import com.workid.domain.model.QRCodeResult
import com.workid.domain.repository.QRCodeRepository
import javax.inject.Inject

class QRCodeRepositoryImpl @Inject constructor() : QRCodeRepository {
    
    override suspend fun validateQRCode(qrData: String): QRCodeResult {
        return try {
            kotlinx.coroutines.delay(500)
            
            // Mock validation logic
            // In real implementation, this would call an API to validate the QR code
            val isValid = qrData.startsWith("WORKID-EMP-")
            
            val employeeId = if (isValid) {
                qrData.removePrefix("WORKID-EMP-")
            } else {
                null
            }
            
            QRCodeResult(
                qrData = qrData,
                timestamp = System.currentTimeMillis(),
                isValid = isValid,
                employeeId = employeeId
            )
        } catch (e: Exception) {
            QRCodeResult(
                qrData = qrData,
                timestamp = System.currentTimeMillis(),
                isValid = false,
                employeeId = null
            )
        }
    }
}
