package jereko.dev

import io.javalin.Javalin

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val app = Javalin.create { config ->
        config.http.generateEtags = true
        config.http.brotliAndGzipCompression(
            9,
            9,
        )
        config.jetty.modifyHttpConfiguration { http ->
            http.sendXPoweredBy = false
        }
    }

    addRoutes(app)

    app.start(port)
}
