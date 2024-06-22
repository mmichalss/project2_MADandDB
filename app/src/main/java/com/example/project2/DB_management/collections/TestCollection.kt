package com.example.project2.DB_management.collections

import com.example.project2.DB_management.common_types.TestType

data class TestCollection(
    val id: Long,
    val testType: TestType,
    val testTarget: String,
    val description: String
)
