package com.example.project2.DB_management.collections


/**
 * Data class for the user collection.
 *
 * @property id The id of the user.
 * @property username The username of the user.
 * @property password The password of the user.
 */
data class UserCollection (
    val id: Long,
    val username: String,
    val password: String
)