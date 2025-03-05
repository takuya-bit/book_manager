package com.example.book_manager.dto

import java.time.LocalDate
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

/**
 * 著者の更新情報を詰めるDTO
 */
data class AuthorUpdateDto(

    /**
     * 著者名
     */
    val name: String? = null,

    /**
     * 生年月日
     */
    @field:Past(message = "Birth date must be in the past")
    @field:Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$",
        message = "Birth date must be in the format yyyy-MM-dd"
    )
    val birthDate: LocalDate? = null
)