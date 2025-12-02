import util.DayPuzzle
import util.RunContext
import util.loadDayData
import java.util.*
import java.io.File

const val year = 2025
fun main(args: Array<String>) {
    // If there are command line arguments, run the specified days, all days, or the highest implemented day.
    if (args.isNotEmpty()) {
        when (args[0]) {
            "a" -> runAll()
            "r" -> runLatest()
            "d" -> loadAndSaveToday()
            else -> runDay(args[0].toInt())
        }
        return
    }

    // If there aren't command line arguments, prompt the user for input.
    val scanner = Scanner(System.`in`)
    print("Pick a day, \"a\" for all, \"r\" for highest implemented, " +
            "or \"d\" to download today's data: ")
    when (val input = scanner.nextLine()) {
        "a" -> runAll()
        "r", "" -> runLatest()
        "d" -> loadAndSaveToday()
        else -> runDay(input.toInt())
    }
}

fun runAll() {
    val startTime = System.currentTimeMillis()
    for (day in 1..25) {
        runDay(day, false)
    }
    val endTime = System.currentTimeMillis()
    println("Total time: ${(endTime - startTime) / 1000.0} seconds")
}
/**
 * Run a specific day.
 */
fun runDay(day: Int, debug: Boolean = true) {
    try {
        val dayString = day.toString().padStart(2, '0')
        val dayClass = Class.forName("adventOfCode$year.Day$dayString")
        val dayConstructor = dayClass.getConstructor(String::class.java, RunContext::class.java)

        // If the input file doesn't exist, download it.
        if (!File("src/main/resources/Day${dayString}.txt").exists()) {
            println("Day $day (data not found, downloading)")
            loadAndSaveDate(year, day)
        }

        // If the test file exists, run the day with the test input first.
        if (debug && File("src/main/resources/Day${dayString}test.txt").exists()) {
            val dayData = File("src/main/resources/Day${dayString}test.txt").readText()
            val dayInstance = dayConstructor.newInstance(dayData, RunContext.TEST)
            println("Day $day (test)")
            dayInstance.javaClass.getMethod("run").invoke(dayInstance)
        }

        // Run the day with the real input.
        val dayData = File("src/main/resources/Day${dayString}.txt").readText()
        val dayInstance = dayConstructor.newInstance(dayData, if (debug) RunContext.ONE else RunContext.PROD)
        println("Day $day")
        dayInstance.javaClass.getMethod("run").invoke(dayInstance)
    } catch (e: ClassNotFoundException) {
        println("Day $day\n\tNot Implemented")
    }
}

/**
 * Run the highest implemented day.
 */
fun runLatest() {
    // TODO: would love to know if there's a better way to do this
    for (day in 2..26) {
        try {
            val dayString = day.toString().padStart(2, '0')
            Class.forName("adventOfCode$year.Day$dayString")
        } catch (e: ClassNotFoundException) {
            runDay(day - 1)
            break
        }
    }
}

fun loadAndSaveToday() {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("America/New_York") // puzzle input is released at midnight EST
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val dayString = day.toString().padStart(2, '0')
    if (File("src/main/resources/Day${dayString}.txt").exists()) {
        println("Day $day\n\tInput already downloaded")
        return
    }

    val dayData = loadAndSaveDate(year, day)
    println("Day $day")
    println("Example input:")
    println(dayData.exampleInput)
    println("Input:")
    println(dayData.input)
}

fun loadAndSaveDate(year: Int, day: Int): DayPuzzle {
    print("\tDownloading day $day data... ")
    val dayData = loadDayData(year, day)
    val dayString = day.toString().padStart(2, '0')
    File("src/main/resources/day${dayString}test.txt").writeText(dayData.exampleInput + "\n")
    File("src/main/resources/day${dayString}.txt").writeText(dayData.input)
    println("done")
    println("\tExample input: ${dayData.exampleInput.split("\n").size} lines")
    println("\tInput: ${dayData.input.split("\n").size} lines")
    println("\tInputs saved to src/main/resources/day${dayString}*.txt")
    return dayData
}
