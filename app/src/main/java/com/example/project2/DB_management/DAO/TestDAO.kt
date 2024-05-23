package com.example.project2.DB_management.DAO

import com.example.project2.DB_management.dto.test.CreateTestDto

interface TestDAO {
    suspend fun insertTestDto(testDto : CreateTestDto): Boolean
}