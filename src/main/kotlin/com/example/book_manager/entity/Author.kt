package com.example.book_manager.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name="author")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val birthDate: LocalDate
)
