package com.example.project2.DB_management.DB_operations

import com.example.project2.DB_management.DAO.ResultDAO
import com.example.project2.DB_management.dto.result.CreateResultDto
import com.example.project2.DB_management.setupConnection
import com.mongodb.MongoException
import org.slf4j.LoggerFactory

class ResultOperations: ResultDAO {
    override suspend fun insertResultDto(resultDto: CreateResultDto): Boolean {
        val logger = LoggerFactory.getLogger(ResultOperations::class.java)
        return try {
            val db = setupConnection()
            val collection = db?.getCollection<CreateResultDto>(collectionName = "result")
            val result = collection?.insertOne(resultDto)
            result!!.wasAcknowledged()
        } catch(e: MongoException){
            e.printStackTrace()
            false
        }
    }
}