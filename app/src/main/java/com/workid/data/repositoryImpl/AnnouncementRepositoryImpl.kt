package com.workid.data.repositoryImpl

import com.workid.domain.model.*
import com.workid.domain.repository.AnnouncementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor() : AnnouncementRepository {
    
    override fun getAnnouncements(): Flow<List<Announcement>> = flow {
        kotlinx.coroutines.delay(500)
        emit(getMockAnnouncements())
    }
    
    override suspend fun getActiveAnnouncements(): List<Announcement> {
        return getMockAnnouncements().filter { it.isActive }
    }
    
    private fun getMockAnnouncements(): List<Announcement> {
        return listOf(
            Announcement(
                id = "ANN001",
                title = "Libur Hari Raya Nyepi",
                content = "Kantor akan tutup pada tanggal 29 Maret 2024 untuk memperingati Hari Raya Nyepi. Karyawan diharapkan menyelesaikan pekerjaan sebelum tanggal tersebut.",
                imageUrl = null,
                publishedDate = "2024-03-15",
                priority = Priority.HIGH,
                isActive = true,
                targetAudience = TargetAudience.ALL
            ),
            Announcement(
                id = "ANN002",
                title = "Program Wellness & Health Check",
                content = "Perusahaan akan mengadakan program health check gratis untuk semua karyawan pada bulan April 2024. Pendaftaran dibuka hingga 25 Maret 2024.",
                imageUrl = "https://example.com/wellness.jpg",
                publishedDate = "2024-03-10",
                priority = Priority.MEDIUM,
                isActive = true,
                targetAudience = TargetAudience.ALL
            ),
            Announcement(
                id = "ANN003",
                title = "Update Sistem HRIS",
                content = "Sistem HRIS akan mengalami maintenance pada tanggal 30 Maret 2024 pukul 22:00 - 02:00 WIB. Fitur absensi dan pengajuan cuti tidak dapat diakses selama periode tersebut.",
                imageUrl = null,
                publishedDate = "2024-03-18",
                priority = Priority.URGENT,
                isActive = true,
                targetAudience = TargetAudience.ALL
            ),
            Announcement(
                id = "ANN004",
                title = "Training Leadership Development",
                content = "Program training leadership untuk level manager dan supervisor akan diadakan pada 15-17 April 2024 di Jakarta. Peserta wajib mendaftar melalui HR portal.",
                imageUrl = null,
                publishedDate = "2024-03-05",
                priority = Priority.MEDIUM,
                isActive = true,
                targetAudience = TargetAudience.MANAGEMENT
            ),
            Announcement(
                id = "ANN005",
                title = "Kebijakan Work From Home Terbaru",
                content = "Mulai 1 April 2024, kebijakan WFH diperbarui dengan opsi hybrid 3 hari kantor, 2 hari rumah. Silakan koordinasi dengan manajer masing-masing.",
                imageUrl = null,
                publishedDate = "2024-02-28",
                priority = Priority.HIGH,
                isActive = false,
                targetAudience = TargetAudience.STAFF
            )
        )
    }
}
