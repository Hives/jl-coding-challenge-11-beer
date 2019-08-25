import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

private object Json {
    val serializer = jacksonObjectMapper()

    val deserializer = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)

}

fun String.deserializeToPubs() = Json.deserializer.readValue<Pubs>(this)
fun <T> T.serialize(): String = Json.serializer.writeValueAsString(this)
