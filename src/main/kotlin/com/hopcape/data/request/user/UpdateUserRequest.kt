package com.hopcape.data.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val fullName: String = "",
    val phoneNumber: String = "",
    val profilePic: String = ""
)
