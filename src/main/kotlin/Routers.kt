package jereko.dev

import io.javalin.Javalin
import io.javalin.http.Context

enum class Method {
    GET, POST, PUT, DELETE, PATCH, OPTIONS
}

data class Route(val path: String, val method: Method, val callback: (ctx: Context) -> Unit)

fun handler(method: Method, path: String, app: Javalin, callback: (ctx: Context) -> Unit) {
    when (method) {
        Method.GET -> app.get(path, callback)
        Method.POST -> app.post(path, callback)
        Method.PUT -> app.put(path, callback)
        Method.DELETE -> app.delete(path, callback)
        Method.PATCH -> app.patch(path, callback)
        Method.OPTIONS -> app.options(path, callback)
    }
}

val routes = listOf(
    Route("/", Method.GET) { ctx ->
        val obj = mapOf(
            "message" to "Hello World!"
        )
        ctx.header("Content-Type", "application/json")
        ctx.json(obj)
    },
    Route("/echo", Method.POST) { ctx ->
        val body = ctx.body()
        ctx.json(body)
    }
)

fun addRoutes(app: Javalin) {
    routes.forEach { route ->
        handler(route.method, route.path, app, route.callback)
    }
}