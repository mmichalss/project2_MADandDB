package com.example.project2.DB_management.dto.result

import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType

/**
 * Data class for the create result DTO.
 *
 * @property testId The id of the test.
 * @property userId The id of the user.
 * @property resultValue The result value.
 * @property timeSpent The time spent on the test.
 */
data class GetResultDto(
    var id: String = "",
    val resultValue: ResultValue = ResultValue.NONE,
    val testId: TestType = TestType.TEST1,
    val timeSpent: Int = 0,
    val userId: String = ""
)
