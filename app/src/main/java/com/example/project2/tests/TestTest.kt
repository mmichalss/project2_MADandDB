package com.example.project2.tests

import com.example.project2.DB_operations.TestOperations
import com.example.project2.common_types.TestType
import com.example.project2.dto.test.CreateTestDto
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    print("Testing insertTestDto()")
    val testOperations = TestOperations()
    val newTest = CreateTestDto(TestType.TEST1, "Parkinson's disease", "This test is used to determine...")
    val result = testOperations.insertTestDto(newTest)
    print("Insertion was successful: $result")
}