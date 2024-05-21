package com.example.project2.collections

import com.example.project2.common_types.TestType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class TestCollection(
    @BsonId val id: ObjectId,
    @BsonProperty("test_type") val testType: TestType,
    @BsonProperty("test_target") val testTarget: String,
    @BsonProperty("description") val description: String
)
