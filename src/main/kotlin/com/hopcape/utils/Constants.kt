package com.hopcape.utils

object Constants {
    private val MONGO_PASSWORD = System.getenv("MONGO_PASSWORD").toString()
    val CONNECTION_STRING = "mongodb+srv://komputersphere:$MONGO_PASSWORD@cluster0.eodoisj.mongodb.net/?retryWrites=true&w=majority"
    const val DB_NAME = "NewsSaaS"
    const val USERS_TABLE_NAME = "user"
    const val ARTICLES_TABLE_NAME = "article"
    const val COMMENTS_TABLE_NAME = "comment"
}