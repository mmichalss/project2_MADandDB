package com.example.project2.app

data class User(
            var id: String="",
            var name: String="",
            var registeredUser: Boolean = false,
            var email: String="",
            var isStudent : Boolean=false,
)