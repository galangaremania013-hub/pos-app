package com.workid.domain.model

data class Announcement(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String?,
    val publishedDate: Long,
    val priority: AnnouncementPriority,
    val isRead: Boolean = false,
    val expiryDate: Long? = null
)

enum class AnnouncementPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}
