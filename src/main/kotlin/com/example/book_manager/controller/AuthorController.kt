package com.example.book_manager.controller

import com.example.book_manager.dto.AuthorRequestDto
import com.example.book_manager.dto.AuthorResponseDto
import com.example.book_manager.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {

    /**
     * 著者情報を登録する
     *
     * @param dto 登録情報
     * @return 登録時のレスポンス情報
     */
    @PostMapping
    fun createAuthor(@RequestBody @Valid dto: AuthorRequestDto): ResponseEntity<String> {
        authorService.createAuthor(dto)
        return ResponseEntity<String>("登録完了", HttpStatus.CREATED)
    }

    /**
     * 指定の著者情報を更新する
     *
     * @param dto 更新情報
     * @return 更新時のレスポンス情報
     */
    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Int, @RequestBody @Valid dto: AuthorRequestDto): ResponseEntity<String> {
        authorService.updateAuthor(id, dto)
        return ResponseEntity<String>("更新完了", HttpStatus.OK)
    }

    /**
     * 指定の著者の詳細情報を取得する
     *
     * @param id 著者ID
     * @return 著者の詳細情報
     */
    @GetMapping("/{id}")
    fun getAuthor(@PathVariable id: Int): ResponseEntity<AuthorResponseDto> {
        val author = authorService.getAuthorById(id)
        return ResponseEntity<AuthorResponseDto>(author, HttpStatus.OK)
    }
}