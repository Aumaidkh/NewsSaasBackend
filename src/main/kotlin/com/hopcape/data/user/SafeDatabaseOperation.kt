package com.hopcape.data.user

suspend fun <T> safeDatabaseOperation(operation: suspend () -> T): T?{
    return try {
        operation()
    }catch (e: Exception){
        println("MongoException: ${e.message}")
        e.printStackTrace()
        null
    }
}