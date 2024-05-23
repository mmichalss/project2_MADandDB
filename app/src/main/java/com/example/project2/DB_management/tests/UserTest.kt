package com.example.project2.DB_management.tests

import com.example.project2.DB_management.DB_operations.UserOperations
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    print("Testing insertUserDto()")
    val userOperations = UserOperations()/*
    val newUser = CreateUserDto("user", "password")
    val result = userOperations.insertUserDto(newUser)
    print("Insertion was successful: $result")*/

    print("Testing getAllUserDtos()")
    val userList = userOperations.findAllUserDtos()
    print("Result: $userList")
}