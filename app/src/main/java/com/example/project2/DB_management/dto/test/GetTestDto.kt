package com.example.project2.DB_management.dto.test

import com.example.project2.DB_management.common_types.TestType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class GetTestDto(
    var id: String = "",
    val testType: TestType = TestType.TEST1,
    val testTarget: String = "",
    val description: String = ""
)
