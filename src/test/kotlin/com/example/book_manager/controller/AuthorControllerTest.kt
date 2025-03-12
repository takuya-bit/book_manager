package com.example.book_manager.controller

import com.example.book_manager.dto.AuthorRegisterDto
import com.example.book_manager.dto.AuthorResponseDto
import com.example.book_manager.dto.AuthorUpdateDto
import com.example.book_manager.service.AuthorService
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest
class AuthorControllerTest {
    // コントローラクラスの動作検証を可能にする
    private lateinit var mockMvc: MockMvc

    // Serviceクラスのモック化
    private val authorService = mock<AuthorService>()

    // テスト対象のControllerクラス
    private val controller = AuthorController(
        authorService
    )

    private val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @BeforeEach
    fun setUp() {
        // MockMVCのインスタンス生成
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Nested
    @DisplayName("著者情報登録処理API")
    inner class Test01 {

        @Nested
        @DisplayName("正常系")
        inner class Test011 {
            @Test
            @DisplayName("正常に登録できること")
            fun test01() {

                val authorRegisterDto = AuthorRegisterDto(
                    name = "テスト太郎",
                    birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                val authorRegisterDtoJson = objectMapper.writeValueAsString(authorRegisterDto)

                doNothing().`when`(authorService).createAuthor(authorRegisterDto)

                mockMvc.perform(
                    post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorRegisterDtoJson)
                )
                    .andExpect(status().isCreated)
                    .andExpect(content().string("Register Completed"))
            }
        }

        @Nested
        @DisplayName("異常系")
        inner class Test012 {
            @Test
            @DisplayName("著者名が空文字の場合")
            fun test02() {

                val authorRegisterDto = AuthorRegisterDto(
                    name = "",
                    birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                val authorRegisterDtoJson = objectMapper.writeValueAsString(authorRegisterDto)

                mockMvc.perform(
                    post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorRegisterDtoJson)
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(content().string("Name must not be blank"))
            }

            @Test
            @DisplayName("生年月日に未来日を指定した場合")
            fun test03() {

                val authorRegisterDto = AuthorRegisterDto(
                    name = "田中太郎",
                    birthDate = LocalDate.parse("2030-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                val authorRegisterDtoJson = objectMapper.writeValueAsString(authorRegisterDto)

                mockMvc.perform(
                    post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorRegisterDtoJson)
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(content().string("Birth date must be in the past"))
            }
        }
    }

    @Nested
    @DisplayName("著者情報更新API")
    inner class Test02 {

        @Nested
        @DisplayName("正常系")
        inner class Test011 {
            @Test
            @DisplayName("正常に更新できること")
            fun test01() {

                val authorUpdateDto = AuthorUpdateDto(
                    name = "テスト太郎",
                    birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                val authorUpdateDtoJson = objectMapper.writeValueAsString(authorUpdateDto)

                doNothing().`when`(authorService).updateAuthor(1, authorUpdateDto)

                mockMvc.perform(
                    put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorUpdateDtoJson)
                )
                    .andExpect(status().isOk)
                    .andExpect(content().string("Update Completed"))
            }
        }

        @Nested
        @DisplayName("異常系")
        inner class Test012 {
            @Test
            @DisplayName("著者名が空文字の場合")
            fun test02() {

                val authorUpdateDto = AuthorUpdateDto(
                    name = "",
                    birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                val authorUpdateDtoJson = objectMapper.writeValueAsString(authorUpdateDto)

                doNothing().`when`(authorService).updateAuthor(1, authorUpdateDto)

                mockMvc.perform(
                    put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorUpdateDtoJson)
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(content().string("Name must not be blank"))
            }

            @Test
            @DisplayName("生年月日に未来日を指定した場合")
            fun test03() {

                val authorUpdateDto = AuthorUpdateDto(
                    name = "テスト太郎",
                    birthDate = LocalDate.parse("2030-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                val authorUpdateDtoJson = objectMapper.writeValueAsString(authorUpdateDto)

                doNothing().`when`(authorService).updateAuthor(1, authorUpdateDto)

                mockMvc.perform(
                    put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorUpdateDtoJson)
                )
                    .andExpect(status().isBadRequest)
                    .andExpect(content().string("Birth date must be in the past"))
            }
        }
    }

    @Nested
    @DisplayName("著者詳細情報取得API")
    inner class Test03 {

        @Nested
        @DisplayName("正常系")
        inner class Test011 {
            @Test
            @DisplayName("正常に取得できること")
            fun test01() {

                // 期待値
                val expected = AuthorResponseDto(
                    id = 1,
                    name = "テスト太郎",
                    birthDate = LocalDate.parse("1990-01-01", DateTimeFormatter.ISO_LOCAL_DATE)
                )

                // サービスクラスの返却値設定
                doReturn(expected).`when`(authorService).getAuthorById(1)

                mockMvc.perform(
                    get("/authors/1")
                )
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").value(expected.id))
                    .andExpect(jsonPath("$.name").value(expected.name))
                    .andExpect(jsonPath("$.birthDate").value(expected.birthDate.toString()))
            }
        }
    }
}