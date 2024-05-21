package com.example.project2.dto.user

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class GetUserDto (
    @BsonId val id: ObjectId,
    @BsonProperty("name") val name: String,
    @BsonProperty("password") val password: String
)