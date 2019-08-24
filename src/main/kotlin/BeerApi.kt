import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class BeerApi() {
    private val serializer = jacksonObjectMapper()

    private val deserializer = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)

    suspend fun getBeers(): String {
        val listOfPubs = getListOfPubs()
        val listOfBeers = listOfPubs.map { it.toListOfBeers() }.flatten().sortedBy { it.name }
        return listOfBeers.toJson()
    }

    suspend fun getListOfPubs(): List<Pub> {
        val pubJson = getPubJson()
        val pubs = pubJson.toPubs()
        val uniquePubs = pubs.sortedByDescending { it.createTS }.distinctBy { it.id } // extract this to it's own function?

        return uniquePubs
    }

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

    fun String.toPubs(): List<Pub> = deserializer.readValue<Pubs>(this).pubs

    fun <T> T.toJson(): String = serializer.writeValueAsString(this)

}

suspend fun main() {
    println(BeerApi().getBeers())
}
