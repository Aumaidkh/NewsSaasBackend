package com.hopcape.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
)