package utils

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.FileReader
import java.io.FileWriter

class CSVUtils {
    companion object{

        val csvMapper = CsvMapper().apply {
            registerModule(KotlinModule())
        }

        inline fun <reified T> writeCsvFile(data: Collection<T>, fileName: String) {
            FileWriter(fileName).use { writer ->
                csvMapper.writer(csvMapper.schemaFor(T::class.java).withHeader())
                    .writeValues(writer)
                    .writeAll(data)
                    .close()
            }
        }
        inline fun <reified T> appendCsvFile(data: T, fileName: String) {
            FileWriter(fileName, true).use { writer ->
                csvMapper.writer(csvMapper.schemaFor(T::class.java))
                    .writeValues(writer)
                    .write(data)
                    .close()
            }
        }

        inline fun <reified T> readCsvFile(fileName: String): List<T> {
            FileReader(fileName).use { reader ->
                return csvMapper
                    .readerFor(T::class.java)
                    .with(CsvSchema.emptySchema().withHeader())
                    .readValues<T>(reader)
                    .readAll()
                    .toList()
            }
        }
    }
}
