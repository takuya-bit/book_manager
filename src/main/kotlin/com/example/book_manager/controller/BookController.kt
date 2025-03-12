package com.example.book_manager.controller

import com.example.book_manager.service.BookService
import com.example.book_manager.dto.BookRegisterDto
import com.example.book_manager.dto.BookUpdateDto
import com.example.book_manager.dto.BookResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    /**
     * 書籍情報を登録する
     *
     * @param dto 登録情報
     * @return 登録時のレスポンス情報
     */
    @PostMapping
    fun createBook(@RequestBody @Valid dto: BookRegisterDto, bindingResult: BindingResult): ResponseEntity<String> {
        if (bindingResult.hasErrors()) {
            return ResponseEntity<String>("validation error", HttpStatus.BAD_REQUEST)
        }
        bookService.createBook(dto)
        return ResponseEntity<String>("Register Completed", HttpStatus.CREATED)
    }

    /**
     * 指定の書籍情報を更新する
     *
     * @param dto 更新情報
     * @return 更新時のレスポンス情報
     */
    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Int, @RequestBody dto: BookUpdateDto, bindingResult: BindingResult): ResponseEntity<String> {
        if (bindingResult.hasErrors()) {
            return ResponseEntity<String>("validation error", HttpStatus.BAD_REQUEST)
        }
        bookService.updateBook(id, dto)
        return ResponseEntity<String>("Update Completed", HttpStatus.OK)
    }

    /**
     * 指定の書籍の詳細情報を取得する
     *
     * @param id 書籍ID
     * @return 書籍の詳細情報
     */
    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Int): ResponseEntity<BookResponseDto> {
        return ResponseEntity<BookResponseDto>(
            bookService.getBook(id),
            HttpStatus.OK
        )
    }
}
