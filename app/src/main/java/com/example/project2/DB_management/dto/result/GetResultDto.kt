package com.example.project2.DB_management.dto.result

import com.example.project2.DB_management.common_types.ResultValue
import com.example.project2.DB_management.common_types.TestType

data class GetResultDto(
    var id: String = "",
    val resultValue: ResultValue = ResultValue.NONE,
    val testId: TestType = TestType.TEST1,
    val timeSpent: Int = 0,
    val userId: String = ""
)
