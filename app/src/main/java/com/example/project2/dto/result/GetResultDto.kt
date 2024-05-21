package com.example.project2.dto.result

import com.example.project2.common_types.ResultValue
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class GetResultDto(
    @BsonId val id: ObjectId,
    @BsonProperty("test_id") val testId: ObjectId,
    @BsonProperty("user_id") val userID: ObjectId,
    @BsonProperty("result_value") val resultValue: ResultValue
)
