package com.example.book_manager.dto

import javax.validation.constraints.*

/**
 * 書籍の更新情報を詰めるDTO
 */
data class BookUpdateDto(
    /**
     * タイトル
     */
    val title: String? = null,

    /**
     * 価格
     */
    @field:Min(0)
    val price: Int? = null,

    /**
     * 出版状況
     */
    @field:Min(0)
    @field:Max(1)
    val publicationStatus: Int? = null,

    /**
     * 著者IDリスト
     */
    @field:Size(min = 1, message = "Author ID list must contain at least one author")
    val authorIds: List<Int>? = null
)