package com.example.project2.DB_management.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class UserCollection (
    val id: Long,
    val username: String,
    val password: String
)