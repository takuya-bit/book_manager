package com.example.book_manager.controller

import com.example.book_manager.dto.AuthorRegisterDto
import com.example.book_manager.dto.AuthorResponseDto
import com.example.book_manager.dto.AuthorUpdateDto
import com.example.book_manager.service.AuthorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

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
    fun createAuthor(@RequestBody @Valid dto: AuthorRegisterDto, bindingResult: BindingResult): ResponseEntity<String> {
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors.joinToString(", ") { it.defaultMessage ?: "Invalid value" }
            return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
        }
        authorService.createAuthor(dto)
        return ResponseEntity<String>("Register Completed", HttpStatus.CREATED)
    }

    /**
     * 指定の著者情報を更新する
     *
     * @param dto 更新情報
     * @return 更新時のレスポンス情報
     */
    @PutMapping("/{id}")
    fun updateAuthor(
        @PathVariable id: Int,
        @RequestBody @Valid dto: AuthorUpdateDto,
        bindingResult: BindingResult
    ): ResponseEntity<String> {
        if (bindingResult.hasErrors()) {
            val errors = bindingResult.allErrors.joinToString(", ") { it.defaultMessage ?: "Invalid value" }
            return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
        }
        authorService.updateAuthor(id, dto)
        return ResponseEntity<String>("Update Completed", HttpStatus.OK)
    }

    /**
     * 指定の著者の詳細情報を取得する
     *
     * @param id 著者ID
     * @return 著者の詳細情報
     */
    @GetMapping("/{id}")
    fun getAuthor(@PathVariable id: Int): ResponseEntity<AuthorResponseDto> {
        return ResponseEntity<AuthorResponseDto>(
            authorService.getAuthorById(id),
            HttpStatus.OK
        )
    }
}