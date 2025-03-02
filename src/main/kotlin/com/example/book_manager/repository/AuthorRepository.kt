package com.example.book_manager.repository

import com.example.generated.Tables.AUTHOR
import com.example.generated.Tables.BOOK_AUTHOR
import com.example.generated.tables.records.AuthorRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class AuthorRepository(private val dslContext: DSLContext) {

    /**
     * 書籍IDをもとに著者のリストを取得する
     *
     * @param bookId 書籍ID
     * @return 著者のリスト
     */
    fun findAuthorsByBookId(bookId: Int): List<AuthorRecord> {
        return dslContext.select()
            .from(AUTHOR)
            .join(BOOK_AUTHOR)
            .on(AUTHOR.ID.eq(BOOK_AUTHOR.AUTHOR_ID))
            .where(BOOK_AUTHOR.BOOK_ID.eq(bookId))
            .fetchInto(AuthorRecord::class.java)
    }

    /**
     * 著者IDの存在チェック
     *
     * @param authorId 著者ID
     * @return 著者レコード
     */
    fun findAuthorByAuthorId(authorId: Int): AuthorRecord? {
        return dslContext.selectFrom(AUTHOR)
            .where(AUTHOR.ID.eq(authorId))
            .fetchOneInto(AuthorRecord::class.java)
    }

    /**
     * 著者情報を登録する
     *
     * @param authorRecord 登録情報
     */
    fun insertAuthor(authorRecord: AuthorRecord) {
        dslContext.insertInto(AUTHOR)
            .set(authorRecord)
            .execute()
    }

    /**
     * 指定の著者情報を取得する
     *
     * @param id 著者ID
     * @return 著者情報
     */
    fun findById(id: Int): AuthorRecord? {
        return dslContext.selectFrom(AUTHOR)
            .where(AUTHOR.ID.eq(id))
            .fetchOne()
    }

    /**
     * 指定の著者情報を更新する
     *
     * @param authorRecord 更新情報
     */
    fun updateAuthor(authorRecord: AuthorRecord) {
        dslContext.update(AUTHOR)
            .set(AUTHOR.NAME, authorRecord.name)
            .set(AUTHOR.BIRTH_DATE, authorRecord.birthDate)
            .where(AUTHOR.ID.eq(authorRecord.id))
            .execute()
    }
}