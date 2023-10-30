package com.hopcape.sockets

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

fun Routing.demoSocket(){
    webSocket("/chat") {
        send("You are connected!")
        for(frame in incoming) {
            frame as? Frame.Text ?: continue
            val receivedText = frame.readText()
            send("You said: $receivedText")
        }
    }
}