package com.example.project2.DB_management.common_types

enum class TestType {
    TEST1, TEST2, TEST3;

    companion object {
        fun fromString(value: String): TestType {
            return try {
                valueOf(value)
            } catch (e: IllegalArgumentException) {
                TEST1
            }
        }
    }
}