package com.example.project2.DB_management.dto.user

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


data class GetUserDto (
    var id: String = "",
    val username: String = "",
    val password: String = ""
){
}