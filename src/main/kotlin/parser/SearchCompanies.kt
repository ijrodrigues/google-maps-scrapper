package parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import khttp.get

class SearchCompanies {
    companion object {
        fun searchAtGoogleMaps(query: String, page: Int): MapsResponse {
            val response: String = get(
                url = "https://google.serper.dev/places",
                headers = mapOf(
                    "X-API-KEY" to "API-KEY",
                    "Content-Type" to "application/json"
                ),
                data = mapOf(
                    "q" to query,
                    "gl" to "br",
                    "hl" to "pt-br",
                    "page" to page
                )
            ).text

            val mapper = jacksonObjectMapper()
            mapper.registerKotlinModule()
            return mapper.readValue(response)
        }
    }
}
