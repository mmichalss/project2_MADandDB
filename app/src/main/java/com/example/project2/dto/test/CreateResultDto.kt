package com.example.project2.dto.test

import com.example.project2.common_types.TestType
import org.bson.codecs.pojo.annotations.BsonProperty

data class CreateTestDto(
    @BsonProperty("test_type") val testType: TestType,
    @BsonProperty("test_target") val testTarget: String,
    @BsonProperty("description") val description: String
)
