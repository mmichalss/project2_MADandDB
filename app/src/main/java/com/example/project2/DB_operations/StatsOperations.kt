package com.example.project2.DB_operations

import com.example.project2.DAO.StatsDAO
import com.example.project2.dto.stats.CreateStatsDto
import com.example.project2.setupConnection
import com.mongodb.MongoException
import org.slf4j.LoggerFactory

class StatsOperations: StatsDAO {
    override suspend fun insertStatsDto(statsDto: CreateStatsDto): Boolean {
            val logger = LoggerFactory.getLogger(StatsOperations::class.java)
            return try {
                val db = setupConnection()
                val collection = db?.getCollection<CreateStatsDto>(collectionName = "stats")
                val result = collection?.insertOne(statsDto)
                result!!.wasAcknowledged()
            } catch(e: MongoException){
                e.printStackTrace()
                false
        }
    }
}