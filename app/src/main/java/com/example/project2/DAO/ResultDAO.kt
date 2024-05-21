package com.example.project2.DAO

import com.example.project2.dto.result.CreateResultDto


interface ResultDAO {
    suspend fun insertResultDto(resultDto: CreateResultDto): Boolean
}