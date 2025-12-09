package adventOfCode2025
import util.*
import kotlin.math.*

@Suppress("unused")
class Day05(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        val split = lines.indexOf("") + 1
        val ranges: List<Pair<Long, Long>> = lines.slice(0..split-2).map { line ->
            line.split('-').map { it.toLong() }.zipWithNext().single()
        }
        var p1 = 0
        for (i in split..lines.lastIndex) {
            val cand = lines[i].toLong()
            for (range in ranges) {
                if (cand in range.first..range.second) {
                    p1++
                    break
                }
            }
        }
        a(p1)
        val sortedRanges = ranges.sortedBy { it.first }
        val mergedRanges = sortedRanges.fold(mutableListOf<Pair<Long, Long>>()) { acc, pair ->
            val last = acc.lastIndex
            if (last != -1 && pair.first <= acc[last].second)
                acc[last] = acc[last].first to max(acc[last].second, pair.second)
            else acc.add(pair)
            acc
        }
        mergedRanges.forEach { plt(it) }
        a(mergedRanges.sumOf { it.second - it.first + 1})
    }
}
