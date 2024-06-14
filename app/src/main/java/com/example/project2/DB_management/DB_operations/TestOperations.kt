package com.example.project2.DB_management.DB_operations

import com.example.project2.DB_management.DAO.TestDAO
import com.example.project2.DB_management.dto.test.CreateTestDto
import com.example.project2.DB_management.dto.test.GetTestDto
import com.example.project2.DB_management.dto.user.GetUserDto
import com.example.project2.DB_management.setupConnection
import com.mongodb.MongoException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
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
    override suspend fun findAllTestDtos(): List<GetTestDto>? {

        val logger = LoggerFactory.getLogger(UserOperations::class.java)
        return try {
            val db = setupConnection()
            val collection = db?.getCollection<GetUserDto>(collectionName = "test")
            var list: List<GetTestDto>?
            runBlocking{
                list = collection?.find<GetTestDto>()?.toList()
            }
            list ?: emptyList()
        } catch(e: MongoException){
            logger.error("Error retrieving tests", e)
            emptyList()
        }
    }
}