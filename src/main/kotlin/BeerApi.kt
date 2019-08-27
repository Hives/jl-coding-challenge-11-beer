class BeerApi() {
    val lng = -0.0395424
    val lat = 51.5131086
    val deg = 0.003
    val pubJsonUrl = "https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=$lng&lat=$lat&deg=$deg"

    fun getBeerJson(): String =
        getListOfPubs()
            .extractBeers()
            .serialize()

    fun getListOfPubs(): List<Pub> =
        deserializeToPubs(pubJsonUrl)
            .pubs
            .uniquify()

    fun List<Pub>.extractBeers(): List<Beer> = this
        .map { pub -> pub.toListOfBeers() }
        .flatten()
        .sortedBy { beer -> beer.name }

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

    fun List<Pub>.uniquify() = this
        .sortedByDescending { it.createTS }
        .distinctBy { it.id }
}

fun main() {
    println(BeerApi().getBeerJson())
}
