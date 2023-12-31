package com.hopcape.di

import com.hopcape.data.article.MongoArticleDatasource
import com.hopcape.data.user.MongoUserDataSource
import com.hopcape.data.user.UserRepositoryImpl
import com.hopcape.data.article.ArticleRepositoryImpl
import com.hopcape.domain.article.ArticleDataSource
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.domain.repository.UserRepository
import com.hopcape.domain.security.hashing.HashingService
import com.hopcape.domain.security.token.TokenService
import com.hopcape.domain.user.UserDataSource
import com.hopcape.security.hashing.SHA256HashingService
import com.hopcape.security.token.JwtTokenService
import com.hopcape.utils.Constants
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    single<MongoDatabase> { MongoClient.create(Constants.CONNECTION_STRING).getDatabase(Constants.DB_NAME) }

    single<UserDataSource> { MongoUserDataSource(get()) }
    single<ArticleDataSource> { MongoArticleDatasource(get()) }

    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::ArticleRepositoryImpl) { bind<ArticleRepository>() }

}

val securityModule = module {


    single<HashingService> { SHA256HashingService() }

    single<TokenService> { JwtTokenService() }
}