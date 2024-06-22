package com.example.project2.DB_management.dto.user

import org.bson.codecs.pojo.annotations.BsonProperty

data class CreateUserDto (
    val username: String,
    val password: String
)
