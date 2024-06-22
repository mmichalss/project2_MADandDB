package com.example.project2.DB_management.collections

import com.example.project2.DB_management.common_types.TestType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class TestCollection(
    val id: Long,
    val testType: TestType,
    val testTarget: String,
    val description: String
)
