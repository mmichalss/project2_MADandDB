package com.example.project2.DB_management.collections

import com.example.project2.DB_management.common_types.TestType

/**
 * Data class for the test collection.
 *
 * @property id The id of the test.
 * @property testType The type of the test.
 * @property testTarget The target of the test.
 * @property description The description of the test.
 */
data class TestCollection(
    val id: Long,
    val testType: TestType,
    val testTarget: String,
    val description: String
)
