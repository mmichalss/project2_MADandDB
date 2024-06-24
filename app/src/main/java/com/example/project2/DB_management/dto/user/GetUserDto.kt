package com.example.project2.DB_management.dto.user


/**
 * Data class for the get user DTO.
 *
 * @property id The id of the user.
 * @property username The username of the user.
 * @property password The password of the user.
 */
data class GetUserDto (
    var id: String = "",
    val username: String = "",
    val password: String = ""
){
}