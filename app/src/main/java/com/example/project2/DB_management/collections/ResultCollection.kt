package com.example.project2.DB_management.collections

import com.example.project2.DB_management.common_types.ResultValue
data class ResultCollection(
    val id: Long,
    val testId: Long,
    val userId: Long,
    val resultValue: ResultValue,
    val timeSpent: Int
)
