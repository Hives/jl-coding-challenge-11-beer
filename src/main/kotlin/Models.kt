data class Pubs(val pubs: List<Pub>)

data class Pub(
    val name: String,
    val id: Int,
    val createTS: String,
    val pubService: String,
    val regularBeers: List<String>?,
    val guestBeers: List<String>?
)

data class Beers(val beers: List<Beer>)

data class Beer(
    val name: String,
    val pubName: String,
    val pubService: String,
    val regularBeer: Boolean
)

// output should be a list of beers with this info:
// - name
// - pubName
// - pubService
// - regularBeer (Boolean)
// beers may appear more than once.
