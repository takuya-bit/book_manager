package com.example.book_manager.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

/**
 * 著者の詳細情報の取得結果を格納するDTO
 */
data class AuthorResponseDto(
    val id: Int?,
    val name: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val birthDate: LocalDate
)
