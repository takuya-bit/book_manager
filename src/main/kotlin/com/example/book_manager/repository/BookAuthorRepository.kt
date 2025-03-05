package com.example.book_manager.repository

import com.example.generated.Tables.BOOK_AUTHOR
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookAuthorRepository(private val dslContext: DSLContext) {

    /**
     * 指定の書籍情報に紐づくレコードを削除する
     *
     * @param bookId 書籍ID
     */
    fun deleteBookAuthorsByBookId(bookId: Int) {
        dslContext.deleteFrom(BOOK_AUTHOR)
            .where(BOOK_AUTHOR.BOOK_ID.eq(bookId))
            .execute()
    }

    /**
     * 引数.著者IDの数だけレコードを登録
     *
     * @param bookId 書籍ID
     * @param authorIds 著者IDリスト
     */
    fun bulkInsertBookAuthors(bookId: Int, authorIds: List<Int>) {
        val records = authorIds.map { authorId ->
            dslContext.newRecord(BOOK_AUTHOR).apply {
                this.bookId = bookId
                this.authorId = authorId
            }
        }

        dslContext.batchInsert(records).execute()
    }
}