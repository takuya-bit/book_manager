package com.example.book_manager.config

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

    @Bean
    fun dslContext(dataSource: DataSource): DSLContext {
        return DSL.using(dataSource.connection)
    }
}
