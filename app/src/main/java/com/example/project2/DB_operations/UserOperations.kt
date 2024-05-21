package com.example.project2.DB_operations

import com.example.project2.DAO.UserDAO
import com.example.project2.dto.user.CreateUserDto
import com.example.project2.setupConnection
import com.mongodb.MongoException
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
            e.printStackTrace()
            false
        }
    }
}