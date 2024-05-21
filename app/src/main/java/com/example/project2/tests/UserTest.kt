package com.example.project2.tests

import com.example.project2.DB_operations.UserOperations
import com.example.project2.dto.CreateUserDto
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    print("Testing insertUserDto()")
    val userOperations = UserOperations()
    val newUser = CreateUserDto("user", "password")
    val result = userOperations.insertUserDto(newUser)
    print("Insertion was successful: $result")
}