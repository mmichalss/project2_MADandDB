package com.example.project2.DB_management.dto.test

import com.example.project2.DB_management.common_types.TestType

/**
 * Data class for the create test DTO.
 *
 * @property testType The type of the test.
 * @property testTarget The target of the test.
 * @property description The description of the test.
 */
data class GetTestDto(
    var id: String = "",
    val testType: TestType = TestType.TEST1,
    val testTarget: String = "",
    val description: String = ""
)
