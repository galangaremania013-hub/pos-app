package com.workid.data.repository

import com.workid.domain.model.Announcement
import com.workid.domain.model.AnnouncementPriority
import com.workid.domain.repository.AnnouncementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnouncementRepositoryImpl @Inject constructor() : AnnouncementRepository {
    
    private val mockAnnouncements = listOf(
        Announcement(
            id = "ann_001",
            title = "Libur Tahun Baru 2024",
            content = "Kantor akan tutup pada tanggal 25 Desember 2023 - 1 Januari 2024. Selamat merayakan!",
            imageUrl = null,
            publishedDate = System.currentTimeMillis() - 86400000 * 7,
            priority = AnnouncementPriority.HIGH,
            isRead = false,
            expiryDate = System.currentTimeMillis() + 86400000 * 7
        ),
        Announcement(
            id = "ann_002",
            title = "Update Aplikasi WorkID v1.1",
            content = "Fitur baru: Export slip gaji ke PDF, perbaikan bug minor, dan peningkatan performa.",
            imageUrl = null,
            publishedDate = System.currentTimeMillis() - 86400000 * 3,
            priority = AnnouncementPriority.NORMAL,
            isRead = false
        ),
        Announcement(
            id = "ann_003",
            title = "Rapat All-Hands Bulanan",
            content = "Akan diadakan pada hari Jumat, 26 Januari 2024 pukul 14:00 WIB via Zoom.",
            imageUrl = null,
            publishedDate = System.currentTimeMillis() - 86400000,
            priority = AnnouncementPriority.URGENT,
            isRead = true
        )
    )
    
    override fun getAnnouncements(): Flow<List<Announcement>> = flow {
        kotlinx.coroutines.delay(300)
        emit(mockAnnouncements.sortedByDescending { it.publishedDate })
    }
    
    override suspend fun markAsRead(announcementId: String): Result<Unit> {
        return Result.success(Unit)
    }
    
    override suspend fun getAnnouncementById(id: String): Result<Announcement> {
        return Result.success(
            mockAnnouncements.firstOrNull { it.id == id }
                ?: throw Exception("Announcement not found")
        )
    }
}
