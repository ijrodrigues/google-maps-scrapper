import parser.JsonProcessor
import java.io.File
import java.nio.file.Paths

fun main() {
    val inputFile = JsonProcessor.getFilesNamesAtResources("/src/main/resources/split/lista-gmaps.csv") // Substitua pelo caminho do seu arquivo CSV
    val linesPerFile = 2000 // Número de registros por arquivo

    val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
    val outputDirectory = Paths.get(projectDirAbsolutePath, "/src/main/resources/split/output")

    val allLines = File(inputFile.first()).readLines() // Lê todas as linhas do arquivo
    val header = allLines.first() // Guarda o cabeçalho
    val dataLines = allLines.drop(1) // Remove o cabeçalho da lista de dados

    val dividedLists = dataLines.chunked(linesPerFile) // Divide os dados em listas menores

    // Cria o diretório de saída se ele não existir
    File(outputDirectory.toString()).mkdir()

    // Cria e escreve em cada arquivo CSV dividido
    dividedLists.forEachIndexed { index, list ->
        val fileName = "$outputDirectory/lista${index + 1}.csv" // Nome do arquivo de saída
        File(fileName).bufferedWriter().use { writer ->
            writer.write(header + "\n") // Escreve o cabeçalho
            list.forEach { writer.write(it + "\n") } // Escreve cada linha da lista atual
        }
    }

    println("Arquivos criados com sucesso no diretório '$outputDirectory'")
}