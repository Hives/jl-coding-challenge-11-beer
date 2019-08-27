data class Pubs(val pubs: List<Pub>)

data class Pub(
    val name: String,
    val id: Int,
    val createTS: String,
    val pubService: String,
    val regularBeers: List<String>?,
    val guestBeers: List<String>?
)

data class Beer(
    val name: String,
    val pubName: String,
    val pubService: String,
    val regularBeer: Boolean
)
