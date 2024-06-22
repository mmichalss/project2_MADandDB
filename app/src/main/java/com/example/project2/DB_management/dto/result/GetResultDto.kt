package com.example.project2.DB_management.dto.result

import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType

data class GetResultDto(
    var id: String,
    val testId: TestType,
    val userId: String,
    val resultValue: ResultValue,
    val timeSpent: Int
)
