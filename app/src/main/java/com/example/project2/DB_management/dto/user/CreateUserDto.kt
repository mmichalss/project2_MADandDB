package com.example.project2.DB_management.dto.user

/**
 * Data class for the create user DTO.
 *
 * @property username The username of the user.
 * @property password The password of the user.
 */
data class CreateUserDto (
    val username: String,
    val password: String
)
