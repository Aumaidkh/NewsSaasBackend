package com.hopcape

import com.hopcape.data.comment.Comment
import com.hopcape.di.appModule
import com.hopcape.di.securityModule
import com.hopcape.domain.comment.CommentDatasource
import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.domain.security.token.TokenService
import com.hopcape.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    System.setProperty("io.ktor.development", "true")
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val commentDatasource by inject<CommentDatasource>()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresAt = TokenService.DEFAULT_TOKEN_EXPIRATION,
        secret = System.getenv("JWT_SECRET")
    )

    install(Koin) {
        slf4jLogger()
        modules(appModule, securityModule)
    }
    configureSerialization()
    configureMonitoring()
    configureSecurity(
        tokenConfig = tokenConfig
    )
    configureRouting(
        tokenConfig = tokenConfig
    )
    GlobalScope.launch {
        commentDatasource.getCommentsFlowFor("653f41399776206797354c3e")
            .collect{
                println("Comments Size: ${it.size}")
            }
    }
    configureSockets()
}
