package com.example.project2.DAO

import com.example.project2.dto.CreateUserDto

interface UserDAO {
    suspend fun insertUserDto(userDto : CreateUserDto): Boolean
}