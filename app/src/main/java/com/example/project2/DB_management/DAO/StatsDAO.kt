package com.example.project2.DB_management.DAO

import com.example.project2.DB_management.dto.stats.CreateStatsDto

interface StatsDAO {
    suspend fun insertStatsDto(statsDto : CreateStatsDto): Boolean
}