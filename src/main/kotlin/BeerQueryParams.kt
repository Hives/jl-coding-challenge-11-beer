internal data class BeerQueryParams (val lng: String?, val lat: String?, val deg: String?) {
    fun validate() = lng?.toFloatOrNull() != null
            && lat?.toFloatOrNull() != null
            && deg?.toFloatOrNull() != null
}
