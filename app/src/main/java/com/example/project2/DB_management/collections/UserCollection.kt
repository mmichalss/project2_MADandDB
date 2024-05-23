package com.example.project2.DB_management.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class UserCollection (
    @BsonId val id: ObjectId,
    @BsonProperty("name") val name: String,
    @BsonProperty("password") val password: String
)