package com.example.book_manager.dto

import javax.validation.constraints.*

/**
 * 書籍の登録情報を詰めるDTO
 */
data class BookRegisterDto(
    /**
     * タイトル
     */
    @field:NotBlank(message = "Title must not be blank")
    @field:Size(max = 254)
    val title: String,

    /**
     * 価格
     */
    @field:Min(0)
    val price: Int,

    /**
     * 出版状況
     */
    @field:Min(0)
    @field:Max(1)
    val publicationStatus: Int,

    /**
     * 著者IDリスト<br>
     */
    @field:Size(min = 1, message = "Author ID list must contain at least one author")
    val authorIds: List<Int>
)