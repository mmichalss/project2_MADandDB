package com.example.project2.DB_management.DB_operations

import com.example.project2.DB_management.DAO.UserDAO
import com.example.project2.DB_management.dto.user.CreateUserDto
import com.example.project2.DB_management.dto.user.GetUserDto
import com.example.project2.DB_management.setupConnection
import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class UserOperations: UserDAO {

    override suspend fun insertUserDto(userDto: CreateUserDto): Boolean {
        val logger = LoggerFactory.getLogger(UserOperations::class.java)
        return try {
            val db = setupConnection()
            val collection = db?.getCollection<CreateUserDto>(collectionName = "user")
            val result = collection?.insertOne(userDto)
            result!!.wasAcknowledged()
        } catch(e: MongoException){
            logger.error("Error inserting user", e)
            false
        }
    }

    override suspend fun findAllUserDtos(): List<GetUserDto>? {

        val logger = LoggerFactory.getLogger(UserOperations::class.java)
        return try {
            val db = setupConnection()
            val collection = db?.getCollection<GetUserDto>(collectionName = "user")
            var list: List<GetUserDto>?
            runBlocking{
            list = collection?.find<GetUserDto>()?.toList()
            }
            list ?: emptyList()
        } catch(e: MongoException){
            logger.error("Error retrieving users", e)
            emptyList()
        }
    }
}