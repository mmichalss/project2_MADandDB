package com.example.project2.DB_management.DB_operations

import com.example.project2.DB_management.DAO.TestDAO
import com.example.project2.DB_management.dto.test.CreateTestDto
import com.example.project2.DB_management.setupConnection
import com.mongodb.MongoException
import org.slf4j.LoggerFactory

class TestOperations: TestDAO {
    override suspend fun insertTestDto(testDto: CreateTestDto): Boolean {
        val logger = LoggerFactory.getLogger(TestOperations::class.java)
        return try {
            val db = setupConnection()
            val collection = db?.getCollection<CreateTestDto>(collectionName = "test")
            val result = collection?.insertOne(testDto)
            result!!.wasAcknowledged()
        } catch(e: MongoException){
            e.printStackTrace()
            false
        }
    }
}