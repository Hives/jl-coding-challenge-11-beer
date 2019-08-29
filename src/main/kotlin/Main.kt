import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun BeerServer() = BeerApp().asServer(Jetty(9000))

fun BeerApp() = routes(
    "/ping" bind Method.GET to { _: Request -> Response(OK) },
    "/beer" bind Method.GET to { request: Request ->
        beeeer(request)
    }
)

fun beeeer(request: Request): Response {
    val lng = request.query("lng")
    val lat = request.query("lat")
    val deg = request.query("deg")
    if (lat == null || lng == null || deg == null) return Response(BAD_REQUEST)
    return Response(OK).body(getBeerJson(lng, lat, deg))
}


fun main() {
    BeerServer().start()
}
