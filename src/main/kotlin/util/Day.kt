// Heavily inspired by SizableShrimp's structure :)
package util

class DayCompleteException(message: String) : Exception(message)

enum class RunContext {
    TEST, ONE, PROD
}
abstract class Day(protected var input: String, protected val context: RunContext = RunContext.PROD) {
    protected val startTime = System.currentTimeMillis()
    protected val lines: List<String>
    protected var part1Solved = false
    protected var part2Solved = false

    init {
        input = parseInput()
        lines = parseToList()
    }

    @Suppress("unused")
            /**
             * Run the day's puzzle, parsing input if neccessary and printing the result.
             */
    fun run() {
        try {
            solve()
        } catch (_: DayCompleteException) {}
    }

    /**
     * Solve the day's puzzle.
     */
    abstract fun solve()

    /**
     * Parse input to a list of strings.
     */
    fun parseToList(): List<String> {
        val lines = input.lines()
        if (lines.last().isEmpty()) return lines.dropLast(1)
        return lines
    }

    /**
     * Parse input to a string.
     */
    fun parseInput(): String {
        if (input.lines().size == 2) {
            // single line
            return input.trim('\n', '\r', ' ')
        }
        return input
    }

    /**
     * Represents solving a part of the day's puzzle. Prints immediately to the console.
     */
    fun a(inp: Any) {
        val endTime = System.currentTimeMillis()
        if (!part1Solved) {
            println("\tPart 1: $inp (${(endTime - startTime) / 1000.0}s)")
            part1Solved = true
            return
        }
        if (!part2Solved) {
            println("\tPart 2: $inp (${(endTime - startTime) / 1000.0}s)")
            part2Solved = true
            return
        }
        println("\tAddt'l: $inp (${(endTime - startTime) / 1000.0}s)")
    }

    /**
     * Represents solving both parts of the day's puzzle. Prints immediately to the console.
     */
    fun a(vararg inp: Any) {
        for (i in inp) {
            a(i)
        }
    }

    /**
     * Print a message to the console if the day is in test mode. Useful for debugging.
     */
    fun plt(vararg message: Any) {
        if (context == RunContext.TEST) println(message.joinToString(" "))
    }
    fun pt(vararg message: Any) {
        if (context == RunContext.TEST) print(message.joinToString(" "))
    }

    /**
     * Print a message to the console if the day is in test or one mode. Useful for debugging.
     */
    fun pl(vararg message: Any) {
        if (context != RunContext.PROD) println(message.joinToString(" "))
    }
    fun p(vararg message: Any) {
        if (context != RunContext.PROD) print(message.joinToString(" "))
    }
}
