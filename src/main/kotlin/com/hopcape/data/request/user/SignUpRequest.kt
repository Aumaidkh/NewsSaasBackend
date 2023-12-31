package com.hopcape.data.request.user

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val profilePic: String = "",
    val phoneNumber: String = ""
)
