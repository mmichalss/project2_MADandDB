package com.example.project2.DB_management.tests

import com.example.project2.DB_management.DB_operations.TestOperations
import com.example.project2.DB_management.common_types.TestType
import com.example.project2.DB_management.dto.test.CreateTestDto
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    //print("Testing insertTestDto()")
    val testOperations = TestOperations()
    /*val newTest = CreateTestDto(TestType.TEST1, "Parkinson's disease", "This test is used to determine...")
    val result = testOperations.insertTestDto(newTest)
    print("Insertion was successful: $result")

    */

    print("Testing getAllUserDtos()")
    val testList = testOperations.findAllTestDtos()!!
    print("Result: ${testList}")
}