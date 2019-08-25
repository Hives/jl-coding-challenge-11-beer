import io.ktor.client.HttpClient
import io.ktor.client.request.get

class BeerApi() {
    suspend fun getBeerJson(): String =
        getListOfPubs()
            .extractBeers()
            .serialize()

    suspend fun getListOfPubs(): List<Pub> =
        getPubJson()
            .deserializeToPubs()
            .pubs
            .uniquify()

    fun List<Pub>.uniquify() = this
        .sortedByDescending { it.createTS }
        .distinctBy { it.id }

    fun List<Pub>.extractBeers(): List<Beer> = this
        .map { pub -> pub.toListOfBeers() }
        .flatten()
        .sortedBy { beer -> beer.name }

    suspend fun getPubJson(): String {
        val url = "https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=-0.141499&lat=51.496466&deg=0.003"
        val client = HttpClient()
        val content = client.get<String>(url)
        client.close()
        return content
    }

    fun Pub.toListOfBeers(): List<Beer> {
        val regularBeerDetails = this.regularBeers?.map { beer -> beer to true } ?: emptyList()
        val guestBeerDetails = this.guestBeers?.map { beer -> beer to false } ?: emptyList()
        val allBeerDetails = regularBeerDetails + guestBeerDetails
        val allBeers = allBeerDetails.map { beer ->
            Beer(
                name = beer.component1(),
                pubName = this.name,
                pubService = this.pubService,
                regularBeer = beer.component2()
            )
        }
        return allBeers
    }
}

suspend fun main() {
    println(BeerApi().getBeerJson())
}
