package com.example.project2.DB_management.DAO

import com.example.project2.DB_management.dto.user.CreateUserDto
import com.example.project2.DB_management.dto.user.GetUserDto

interface UserDAO {
    suspend fun insertUserDto(userDto : CreateUserDto): Boolean
    suspend fun findAllUserDtos(): List<GetUserDto>?
}