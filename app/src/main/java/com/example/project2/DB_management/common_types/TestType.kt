package com.example.project2.DB_management.common_types

/**
 * Enum class for the test type.
 */
enum class TestType {
    TEST1, TEST2, TEST3;

    /**
     * Converts the enum to a string.
     *
     * @return The string representation of the enum.
     */
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