package com.example.book_manager.service

import com.example.book_manager.dto.BookRegisterDto
import com.example.book_manager.dto.BookResponseDto
import com.example.book_manager.dto.BookUpdateDto
import com.example.book_manager.exception.InvalidPublicationStatusException
import com.example.book_manager.model.PublicationStatus
import com.example.book_manager.repository.AuthorRepository
import com.example.book_manager.repository.BookAuthorRepository
import com.example.book_manager.repository.BookRepository
import com.example.generated.tables.records.BookRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val bookAuthorRepository: BookAuthorRepository,
    private val authorRepository: AuthorRepository
) {

    /**
     * 書籍情報を登録する
     *
     * @param dto 登録情報
     * @return 登録時のレスポンス情報
     */
    @Transactional
    fun createBook(dto: BookRegisterDto) {
        // 書籍テーブルへの登録情報を設定する
        val bookRecord = BookRecord().apply {
            title = dto.title
            price = dto.price
            publicationStatus = dto.publicationStatus
        }

        // 書籍テーブルへの登録処理と自動インクリメントされたIDの取得
        val insertedBookId = bookRepository.insertBookAndGetId(bookRecord)

        // 登録情報.著者IDリストが値ありの場合
        if (dto.authorIds.isNotEmpty()) {
            // 著者IDの数だけ書籍_著者テーブルにレコードを登録する
            bookAuthorRepository.bulkInsertBookAuthors(insertedBookId, dto.authorIds)
        }
    }

    /**
     * 指定の書籍の詳細情報を取得する
     *
     * @param id 書籍ID
     * @return 書籍の詳細情報
     */
    @Transactional(readOnly = true)
    fun getBook(id: Int): BookResponseDto {
        // パスパラメータ.書籍IDをもとに詳細情報を取得する（存在チェックも兼ねる）
        val bookRecord = bookRepository.findById(id) ?: throw NoSuchElementException("Book not found")

        // 紐づく著者のレコードを取得する
        val authorRecords = authorRepository.findAuthorsByBookId(bookRecord.id)

        // 取得した著者のレコードから著者名リストを作成する
        val authorNames = if (authorRecords.isNotEmpty()) {
            authorRecords.map { it.name }
        } else {
            emptyList()
        }

        // レスポンス返却
        return BookResponseDto(
            id = bookRecord.id,
            title = bookRecord.title,
            price = bookRecord.price,
            publicationStatus = bookRecord.publicationStatus,
            authorNameList = authorNames
        )
    }

    /**
     * 指定の書籍情報を更新する
     *
     * @param id 書籍ID
     * @param dto 更新情報
     * @return 更新時のレスポンス情報
     */
    @Transactional
    fun updateBook(id: Int, dto: BookUpdateDto) {
        // パスパラメータ.書籍IDをもとに書籍情報を取得する（存在チェックも兼ねる）
        val bookRecord = bookRepository.findById(id) ?: throw NoSuchElementException("Book not found")

        // 更新情報.出版状況に対するバリデーションチェック
        if (dto.publicationStatus != null) {
            validatePublicationStatus(bookRecord.publicationStatus, dto.publicationStatus)
        }

        // 更新情報.著者IDの存在チェック
        dto.authorIds?.forEach { authorId ->
            authorRepository.findAuthorByAuthorId(authorId)
                ?: throw NoSuchElementException("Author with ID $authorId not found")
        }

        // 書籍テーブルの更新
        bookRecord.apply {
            title = dto.title ?: this.title
            price = dto.price ?: this.price
            publicationStatus = dto.publicationStatus ?: this.publicationStatus
        }
        bookRepository.updateBook(bookRecord)

        // 更新情報.著者IDリストが指定ありの場合
        if (!dto.authorIds.isNullOrEmpty()) {
            // パスパラメータ.書籍IDに紐づく書籍_著者テーブルのレコードを削除
            bookAuthorRepository.deleteBookAuthorsByBookId(id)
            // 著者IDの数だけ書籍_著者テーブルにレコードを登録する
            bookAuthorRepository.bulkInsertBookAuthors(id, dto.authorIds)
        }

    }

    /**
     * 出版状況に関するバリデーションチェック
     *
     * @param currentStatus 現在の出版状況
     * @param newStatus 更新情報.出版状況
     * @throws InvalidPublicationStatusException 出版済みの書籍を未出版に更新しようとした場合の例外クラス
     */
    private fun validatePublicationStatus(currentStatus: Int, newStatus: Int?) {
        if (newStatus == PublicationStatus.UNPUBLISHED.value && currentStatus == PublicationStatus.PUBLISHED.value) {
            throw InvalidPublicationStatusException("Cannot update a published book to unpublished status.")
        }
    }
}
