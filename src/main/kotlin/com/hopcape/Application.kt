package com.hopcape

import com.hopcape.di.appModule
import com.hopcape.di.securityModule
import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.domain.security.token.TokenService
import com.hopcape.plugins.configureMonitoring
import com.hopcape.plugins.configureRouting
import com.hopcape.plugins.configureSecurity
import com.hopcape.plugins.configureSerialization
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    System.setProperty("io.ktor.development", "true")
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
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
}
