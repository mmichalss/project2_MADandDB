package com.example.project2.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class StatsCollection(
    @BsonId val id: ObjectId,
    @BsonProperty("result_id") val resultId: ObjectId,
    @BsonProperty("user_id") val userId: ObjectId,
    @BsonProperty("time_spent") val timeSpent: Int,
    @BsonProperty("number_of_tries") val numberOfTries: Int
)
