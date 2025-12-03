package adventOfCode2025
import org.apache.commons.lang3.math.NumberUtils.*
import util.*

@Suppress("unused")
class Day03(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        var p1 = 0
        lines.forEach { line ->
            val max1st = IntArray(line.length) { 0 }
            val max2nd = IntArray(line.length) { 0 }
            max1st[0] = line[0] - '0'
            max2nd[line.length - 1] = line.last() - '0'
            for (i in 1 until line.length) {
                max1st[i] = max(max1st[i - 1], line[i] - '0')
            }
            for (i in  line.length - 2 downTo 0) {
                max2nd[i] = max(max2nd[i + 1], line[i] - '0')
            }
            val max = max1st.take(line.length - 1).withIndex().maxOf { (i, it) ->
                it * 10 + max2nd[i + 1]
            }
            plt(line, max)
            p1 += max
        }
        a(p1)

        var p2: Long = 0
        lines.forEach { line ->
            val dp = Array(line.length) { LongArray(13) { 0 } }
            dp[0][1] = (line[0] - '0').toLong()
            for (i in 1 until line.length) {
                val cellVal = line[i] - '0'
                // either take the digit (if we can) or don't
                for (j in 1..min(12, line.length)) {
                    dp[i][j] = max(dp[i - 1][j - 1] * 10 + cellVal, dp[i - 1][j])
                }
            }
            plt(dp.contentDeepToString())
            val max = dp.maxOf { it[12] }
            plt(line, max)
            p2 += max
        }
        a(p2)
    }
}
