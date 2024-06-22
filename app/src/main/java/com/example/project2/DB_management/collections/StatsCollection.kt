package com.example.project2.DB_management.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class StatsCollection(
    val id: Long,
    val resultId: Long,
    val userId: Long,
    val timeSpent: Int,
    val numberOfTries: Int
)
