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
