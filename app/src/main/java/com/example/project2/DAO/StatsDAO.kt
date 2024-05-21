package com.example.project2.DAO

import com.example.project2.dto.stats.CreateStatsDto

interface StatsDAO {
    suspend fun insertStatsDto(statsDto : CreateStatsDto): Boolean
}