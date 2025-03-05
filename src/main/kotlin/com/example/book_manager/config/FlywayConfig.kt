//package com.example.book_manager.config
//
//import org.flywaydb.core.Flyway
//import org.springframework.boot.autoconfigure.flyway.FlywayProperties
//import org.springframework.boot.context.properties.EnableConfigurationProperties
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import javax.sql.DataSource
//
//@Configuration
//@EnableConfigurationProperties(FlywayProperties::class)
//class FlywayConfig {
//
//    @Bean
//    fun flyway(dataSource: DataSource, flywayProperties: FlywayProperties): Flyway {
//        return Flyway.configure()
//            .dataSource(dataSource)
//            .baselineOnMigrate(true) // 既存のDBに対して最初のマイグレーションを適用可能にする
//            .locations(*flywayProperties.locations.toTypedArray()) // 設定ファイルのマイグレーション場所を適用
//            .load()
//            .also { it.migrate() } // マイグレーションを実行
//    }
//}