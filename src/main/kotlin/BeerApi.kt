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

    fun Pub.toListOfBeers(): List<Beer> =
        (this.regularBeers.map { it to true } + this.guestBeers.map { it to false })
            .map { beerDetails ->
                val (beerName, isRegularBeer) = beerDetails
                Beer(
                    name = beerName,
                    pubName = this.name,
                    pubService = this.pubService,
                    regularBeer = isRegularBeer
                )
            }

    fun List<Pub>.removeDuplicates() = this
        .sortedByDescending { it.createTS }
        .distinctBy { it.id }
}

fun main() {
    println(BeerApi().getBeerJson())
}
