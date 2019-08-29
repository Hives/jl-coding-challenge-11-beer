fun getBeerJson(lng: String, lat: String, deg: String): String {
    return getListOfPubs(lng, lat, deg)
        .extractBeers()
        .serialize()
}

fun getListOfPubs(lng: String, lat: String, deg: String): List<Pub> {
    val pubJsonUrl = "https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=$lng&lat=$lat&deg=$deg"
    return deserializeToPubs(pubJsonUrl).removeDuplicates()
}

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
