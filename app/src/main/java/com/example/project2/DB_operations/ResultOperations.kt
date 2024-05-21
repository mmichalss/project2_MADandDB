package com.example.project2.DB_operations

import com.example.project2.DAO.ResultDAO
import com.example.project2.dto.result.CreateResultDto
import com.example.project2.setupConnection
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