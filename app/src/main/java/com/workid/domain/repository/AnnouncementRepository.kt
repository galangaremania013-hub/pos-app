package com.workid.domain.repository

import com.workid.domain.model.Announcement
import kotlinx.coroutines.flow.Flow

interface AnnouncementRepository {
    fun getAnnouncements(): Flow<List<Announcement>>
    
    suspend fun markAsRead(announcementId: String): Result<Unit>
    
    suspend fun getAnnouncementById(id: String): Result<Announcement>
}
