package parser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CompanyResponse(
    @field:JsonProperty("near_gyms") val nearGyms: List<Company>,
    @field:JsonProperty("_metadata") val metadata: MetadataResponse
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MapsResponse(
    val places: List<Place>?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MetadataResponse(
    @field:JsonProperty("page_count") val pageCount: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Place(
    val title: String?,
    val phoneNumber: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Company(
    val gym: Gym
)

@JsonIgnoreProperties(ignoreUnknown = true)
class Gym(
    val id: String, val title: String, val phone: String?
) {
    override fun toString(): String {
        return "Gym(id='$id', title='$title', phone=$phone)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Gym
        if (phone != other.phone) return false
        return true
    }

    override fun hashCode(): Int {
        return phone?.hashCode() ?: 0
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Geolocation(
    val idmunicipio: Int, val uf: String, val municipio: String, val longitude: String, val latitude: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Geolocation

        if (idmunicipio != other.idmunicipio) return false
        if (uf != other.uf) return false
        if (municipio != other.municipio) return false
        if (longitude != other.longitude) return false
        return latitude == other.latitude
    }

    override fun hashCode(): Int {
        var result = idmunicipio
        result = 31 * result + uf.hashCode()
        result = 31 * result + municipio.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }

    override fun toString(): String {
        return "Geolocation(idmunicipio=$idmunicipio, uf='$uf', municipio='$municipio', longitude=$longitude, latitude=$latitude)"
    }
}