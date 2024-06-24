package com.example.project2.DB_management.collections

import com.example.project2.DB_management.common_types.ResultValue
/**
 * Data class for the result collection.
 *
 * @property id The id of the result.
 * @property testId The id of the test.
 * @property userId The id of the user.
 * @property resultValue The result value.
 * @property timeSpent The time spent on the test.
 */
data class ResultCollection(
    val id: Long,
    val testId: Long,
    val userId: Long,
    val resultValue: ResultValue,
    val timeSpent: Int
)
