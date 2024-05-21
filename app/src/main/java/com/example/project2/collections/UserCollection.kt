package com.example.project2.collections

import org.bson.types.ObjectId

data class UserCollection (
    val id: ObjectId,
    val name: String,
    val password: String
)