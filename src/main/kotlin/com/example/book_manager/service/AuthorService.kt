package com.example.book_manager.service

import com.example.book_manager.dto.AuthorRegisterDto
import com.example.book_manager.dto.AuthorResponseDto
import com.example.book_manager.dto.AuthorUpdateDto
import com.example.book_manager.repository.AuthorRepository
import com.example.generated.tables.records.AuthorRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    /**
     * 書籍情報を登録する
     *
     * @param dto 登録情報
     * @return 登録時のレスポンス情報
     */
    @Transactional
    fun createAuthor(dto: AuthorRegisterDto) {
        // 著者テーブルへの登録情報を設定する
        val authorRecord = AuthorRecord().apply {
            name = dto.name
            birthDate = dto.birthDate
        }

        // 著者テーブルへの登録処理
        authorRepository.insertAuthor(authorRecord)
    }

    /**
     * 指定の著者情報を更新する
     *
     * @param id 著者ID
     * @param dto 更新情報
     */
    @Transactional
    fun updateAuthor(id: Int, dto: AuthorUpdateDto) {
        // 著者IDの存在チェック
        val authorRecord = authorRepository.findAuthorByAuthorId(id) ?: throw NoSuchElementException("Author not found")

        // 著者情報の更新
        authorRecord.apply {
            name = dto.name ?: this.name
            birthDate = dto.birthDate ?: this.birthDate
        }
        authorRepository.updateAuthor(authorRecord)
    }

    /**
     * 指定の著者の詳細情報を取得する
     *
     * @param id 著者ID
     * @return 著者の詳細情報
     */
    @Transactional(readOnly = true)
    fun getAuthorById(id: Int): AuthorResponseDto {
        // パスパラメータ.著者IDをもとに詳細情報を取得する（存在チェックも兼ねる）
        val authorRecord = authorRepository.findById(id) ?: throw NoSuchElementException("Author not found")

        // レスポンス返却
        return AuthorResponseDto(
            id = authorRecord.id,
            name = authorRecord.name,
            birthDate = authorRecord.birthDate
        )
    }
}