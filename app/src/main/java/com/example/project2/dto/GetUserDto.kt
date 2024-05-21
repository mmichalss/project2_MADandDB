package com.example.project2.dto

import org.bson.types.ObjectId

data class GetUserDto (
    val id: ObjectId,
    val name: String,
    val password: String
)