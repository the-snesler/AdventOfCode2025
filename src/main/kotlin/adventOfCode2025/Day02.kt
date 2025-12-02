package adventOfCode2025
import util.*
import kotlin.math.pow
import kotlin.math.roundToLong


@Suppress("unused")
class Day02(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    fun checkRangeP1(bottom: String, top: String): Long {
        if (bottom.length % 2 == 1) return 0
        var out: Long = 0
        for (i in (bottom.toLong())..(top.toLong())) {
            val test = i.toString()
            if (test.take(test.length / 2) == test.substring(test.length / 2)) {
                // pt(i, "")
                out += i
            }
        }
        return out
    }

    fun checkRangeP2(bottom: String, top: String): Long {
        var out: Long = 0
        for (i in (bottom.toLong())..(top.toLong())) {
            val test = i.toString()
            if (test.matches(Regex("(.*)\\1+"))) {
                pt(i, "")
                out += i
            }
        }
        return out
    }

    override fun solve() {
        var p1: Long = 0
        var p2: Long = 0
        input.split(",").map { e -> e.split("-")}.forEach {
            val bottom = it[0]
            val top = it[1]
            pt(bottom, top, "-> ")
            // need to split into many ranges with equal digit counts in top and bottom
            // e.g. 85-1102 becomes 85-99, 100-999, 1000-1102
            val (smaller, larger) = if (bottom.length > top.length)
                Pair(top, bottom) else
                Pair(bottom, top)
            for (i in smaller.length ..larger.length) {
                val lower = if (i == smaller.length) smaller else 10.0.pow((i - 1).toDouble())
                    .roundToLong()
                    .toString()
                val upper = if (i == larger.length) larger else (10.0.pow(i.toDouble()) - 1)
                    .roundToLong()
                    .toString()

                p1 += checkRangeP1(lower,upper)
                p2 += checkRangeP2(lower,upper)
            }

            plt()
        }
        a(p1, p2)
    }
}
