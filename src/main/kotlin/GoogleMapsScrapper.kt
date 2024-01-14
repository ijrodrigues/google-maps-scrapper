import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import parser.Geolocation
import parser.JsonProcessor
import parser.SearchCompanies
import utils.CSVUtils

fun main() = runBlocking {
    val geolocationFile = JsonProcessor.getFilesNamesAtResources("/src/main/resources/geo")

    val terms = listOf("yoga", "crossfit", "treinamento funcional", "quadra de tenis", "escolinha de futebol")

    terms.forEach { term ->
        val queries = geolocationFile.flatMap { CSVUtils.readCsvFile<Geolocation>(it) }
            .map { "$term em ${it.municipio} - ${it.uf}" }

        queries.map { query ->
            async(Dispatchers.Default) { findCompanies(query) }
        }.forEach { it.await() }
    }
}

fun findCompanies(query: String) {
    for (offset in 1..10) {
        val places = findCompaniesAtGoogleMaps(query, offset)
        if (places < 10) {
            println("Fim dos resultados para a querie: $query - página $offset")
            break
        }
    }
}

private fun findCompaniesAtGoogleMaps(query: String, offset: Int): Int {
    val places = SearchCompanies.searchAtGoogleMaps(query, offset).places
    println("\n${places?.size} empresas encontradas, querie:${query} - página $offset\n")

    places?.filter { it.phoneNumber != null }
        ?.forEach {
            println("id: ${it.title}, telefone: ${it.phoneNumber}")
            CSVUtils.appendCsvFile(PlaceResponse(query, it.title, it.phoneNumber), "lista-gmaps.csv")
        }

    return places?.size ?: 0
}

data class PlaceResponse(
    val q: String,
    val title: String?,
    val phoneNumber: String?,
)