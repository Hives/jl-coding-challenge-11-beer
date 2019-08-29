internal fun getBeerJson(params: BeerQueryParams): String {
    return getListOfPubs(params)
        .extractBeers()
        .serialize()
}

internal fun getListOfPubs(params: BeerQueryParams): List<Pub> {
    val pubJsonUrl =
        "https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=${params.lng}&lat=${params.lat}&deg=${params.deg}"
    return deserializeToPubs(pubJsonUrl).removeDuplicates()
}

internal fun List<Pub>.extractBeers() = this
    .flatMap { it.toListOfBeers() }
    .sortedBy { it.name }

internal fun Pub.toListOfBeers(): List<Beer> =
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

internal fun List<Pub>.removeDuplicates() = this
    .sortedByDescending { it.createTS }
    .distinctBy { it.id }
