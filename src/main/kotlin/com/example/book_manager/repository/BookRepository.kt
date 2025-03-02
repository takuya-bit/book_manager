package com.example.book_manager.repository

import com.example.generated.tables.Book.BOOK
import com.example.generated.tables.records.BookRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookRepository(private val dslContext: DSLContext) {

    /**
     * 書籍情報を登録し、自動インクリメントされたIDを取得する
     *
     * @param bookRecord 登録情報
     * @return 登録された書籍のID
     */
    fun insertBookAndGetId(bookRecord: BookRecord): Int {
        return dslContext.insertInto(BOOK)
            .set(bookRecord)
            .returning(BOOK.ID)
            .fetchOne()
            ?.id ?: throw IllegalStateException("Failed to retrieve the inserted book ID")
    }

    /**
     * 指定の書籍情報を取得する
     *
     * @param id 書籍ID
     * @return 書籍情報
     */
    fun findById(id: Int): BookRecord? {
        return dslContext.selectFrom(BOOK)
            .where(BOOK.ID.eq(id))
            .fetchOne()
    }

    /**
     * 指定の書籍情報を更新する
     *
     * @param bookRecord 更新情報
     */
    fun updateBook(bookRecord: BookRecord) {
        dslContext.update(BOOK)
            .set(bookRecord)
            .where(BOOK.ID.eq(bookRecord.id))
            .execute()
    }
}