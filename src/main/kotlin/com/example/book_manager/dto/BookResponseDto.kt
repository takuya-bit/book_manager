package com.example.book_manager.dto

/**
 * 書籍の詳細情報の取得結果を格納するDTO
 */
data class BookResponseDto(
    val id: Int?,
    val title: String,
    val price: Int,
    val publicationStatus: Int,
    val authorNameList: List<String>?
)
