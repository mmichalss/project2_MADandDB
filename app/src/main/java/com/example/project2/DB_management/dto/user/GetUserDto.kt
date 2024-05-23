package com.example.project2.DB_management.dto.user

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


data class GetUserDto (
    @BsonId
    val id: ObjectId = ObjectId(),
    @BsonProperty("name")
    val name: String = "",
    @BsonProperty("password")
    val password: String = ""
){
}