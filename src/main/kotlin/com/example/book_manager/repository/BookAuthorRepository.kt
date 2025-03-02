package com.example.book_manager.repository

import com.example.generated.Tables.BOOK_AUTHOR
import com.example.generated.tables.records.BookAuthorRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookAuthorRepository(private val dslContext: DSLContext) {

    /**
     * 書籍と著者の紐づけを登録する
     *
     * @param bookId 書籍ID
     * @param authorId 著者ID
     */
    fun insertBookAuthor(bookId: Int, authorId: Int) {
        val bookAuthorRecord = BookAuthorRecord().apply {
            this.bookId = bookId
            this.authorId = authorId
        }
        dslContext.insertInto(BOOK_AUTHOR)
            .set(bookAuthorRecord)
            .execute()
    }

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
}