package com.example.project2.DB_management.collections

data class StatsCollection(
    val id: Long,
    val resultId: Long,
    val userId: Long,
    val timeSpent: Int,
    val numberOfTries: Int
)
