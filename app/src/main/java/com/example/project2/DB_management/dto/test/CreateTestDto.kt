package com.example.project2.DB_management.dto.test

import com.example.project2.DB_management.common_types.TestType
import org.bson.codecs.pojo.annotations.BsonProperty

data class CreateTestDto(
    val testType: TestType,
    val testTarget: String,
    val description: String
)
