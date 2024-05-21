package com.example.project2.DAO

import com.example.project2.dto.test.CreateTestDto

interface TestDAO {
    suspend fun insertTestDto(testDto : CreateTestDto): Boolean
}