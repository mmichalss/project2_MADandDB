package com.example.project2.DB_management.tests

import com.example.project2.DB_management.DB_operations.UserOperations
import com.example.project2.DB_management.dto.user.GetUserDto
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    print("Testing insertUserDto()")
    val userOperations = UserOperations()/*
    val newUser = CreateUserDto("user", "password")
    val result = userOperations.insertUserDto(newUser)
    print("Insertion was successful: $result")*/

    runBlocking {
        print("Testing getAllUserDtos()")
        val userList =userOperations.findAllUserDtos()!!
        print("Result: ${userList}")
    }
}