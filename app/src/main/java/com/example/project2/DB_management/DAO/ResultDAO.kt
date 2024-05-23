package com.example.project2.DB_management.DAO

import com.example.project2.DB_management.dto.result.CreateResultDto


interface ResultDAO {
    suspend fun insertResultDto(resultDto: CreateResultDto): Boolean
}