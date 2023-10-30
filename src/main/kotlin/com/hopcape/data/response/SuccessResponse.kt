package com.hopcape.data.response

import kotlinx.serialization.Serializable


@Serializable
data class SuccessResponse<T>(
    val data: T
)