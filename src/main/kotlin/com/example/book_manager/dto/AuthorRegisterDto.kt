package com.example.book_manager.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

/**
 * 著者の登録情報を詰めるDTO
 */
data class AuthorRegisterDto(

    /**
     * 著者名
     */
    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    /**
     * 生年月日
     */
    @field:Past(message = "Birth date must be in the past")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate
)