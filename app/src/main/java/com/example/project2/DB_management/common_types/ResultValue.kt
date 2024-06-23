package com.example.project2.DB_management.common_types

enum class ResultValue {
    NONE, SMALL, MEDIUM, HIGH;

    companion object {
        fun fromString(value: String): ResultValue {
            return try {
                valueOf(value)
            } catch (e: IllegalArgumentException) {
                NONE
            }
        }
    }
}