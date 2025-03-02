package com.example.book_manager.dto

import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

data class AuthorRequestDto(

    /**
     * 著者名
     */
    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    /**
     * 生年月日
     */
    @field:NotNull(message = "Birth date must not be null")
    @field:Past(message = "Birth date must be in the past")
    @field:Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2}$",
        message = "Birth date must be in the format yyyy-MM-dd"
    )
    val birthDate: LocalDate
)