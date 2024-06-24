package com.example.project2.DB_management.common_types

/**
 * Enum class for the result value.
 */
enum class ResultValue {
    NONE, SMALL, MEDIUM, HIGH;

    /**
     * Converts the enum to a string.
     *
     * @return The string representation of the enum.
     */
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