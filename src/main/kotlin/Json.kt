import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL

object Json {
    val serializer = jacksonObjectMapper()

    val deserializer = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)

}

fun deserializeToPubs(urlString: String) = Json.deserializer.readValue<Pubs>(URL(urlString)).pubs
fun <T> T.serialize(): String = Json.serializer.writeValueAsString(this)
