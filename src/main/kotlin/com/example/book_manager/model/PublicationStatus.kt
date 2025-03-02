package com.example.book_manager.model


enum class PublicationStatus(val value: Int) {
    UNPUBLISHED(0), // 未出版
    PUBLISHED(1); // 出版済み

    companion object {
        fun fromValue(value: Int): PublicationStatus {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Invalid publication status: $value")
        }
    }
}