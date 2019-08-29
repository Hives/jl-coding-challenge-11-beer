class BeerApi() {
    val lng = -0.0395424
    val lat = 51.5131086
    val deg = 0.003
    val pubJsonUrl = "https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=$lng&lat=$lat&deg=$deg"

    fun getBeerJson(): String =
        getListOfPubs()
            .extractBeers()
            .serialize()

    fun getListOfPubs() = deserializeToPubs(pubJsonUrl).removeDuplicates()

    fun List<Pub>.extractBeers() = this
        .flatMap { it.toListOfBeers() }
        .sortedBy { it.name }

    fun Pub.toListOfBeers(): List<Beer> {
        val pub = this
        val regularBeerDetails = pub.regularBeers?.map { beerName -> beerName to true } ?: emptyList()
        val guestBeerDetails = pub.guestBeers?.map { beerName -> beerName to false } ?: emptyList()
        return (regularBeerDetails + guestBeerDetails).map { beerDetails ->
            val (beerName, isRegularBeer) = beerDetails
            Beer(
                name = beerName,
                pubName = pub.name,
                pubService = pub.pubService,
                regularBeer = isRegularBeer
            )
        }
    }

    fun List<Pub>.removeDuplicates() = this
        .sortedByDescending { it.createTS }
        .distinctBy { it.id }
}

fun main() {
    println(BeerApi().getBeerJson())
}
