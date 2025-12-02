package util
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.*
import okhttp3.OkHttpClient
import okhttp3.Request

data class DayPuzzle(var exampleInput: String = "", var input: String = "")

fun loadDayData(year: Int, day: Int): DayPuzzle {
    val websiteUrl = "https://adventofcode.com/$year/day/$day"
    if (System.getenv("AOC_SESSION") == null) {
        throw IllegalStateException("AOC_SESSION environment variable not set")
    }

    // Getting the example input
    val puzzle = skrape(HttpFetcher) {

        request {
            url = websiteUrl
            cookies = mapOf("session" to System.getenv("AOC_SESSION"))
        }

        extractIt<DayPuzzle> { it ->
            htmlDocument {
                it.exampleInput = pre {
                    findFirst {
                        text
                    }
                }
            }
        }
    }

    // This is the part where we get the real input
    val inputUrl = "$websiteUrl/input"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(inputUrl)
        .addHeader("Cookie", "session=${System.getenv("AOC_SESSION")}")
        .build()
    try {
        val response = client.newCall(request).execute()
        puzzle.input = response.body?.string() ?: ""
    } catch (e: Exception) {
        println("Error fetching input: $e")
    }
    // TODO: Maybe I don't need two completely separate libraries for this?
    return puzzle
}
