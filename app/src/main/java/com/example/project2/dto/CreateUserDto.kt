package com.example.project2.dto

import org.bson.codecs.pojo.annotations.BsonProperty

data class CreateUserDto (
    @BsonProperty("name")val name: String,
    @BsonProperty("password")val password: String
)