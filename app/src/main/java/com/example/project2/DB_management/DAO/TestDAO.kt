package com.example.project2.DB_management.DAO

import com.example.project2.DB_management.dto.test.CreateTestDto
import com.example.project2.DB_management.dto.test.GetTestDto

interface TestDAO {
    suspend fun insertTestDto(testDto : CreateTestDto): Boolean
    suspend fun findAllTestDtos(): List<GetTestDto>?
}