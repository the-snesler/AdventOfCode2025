package adventOfCode2025
import util.*
import kotlin.math.abs
import kotlin.math.max

@Suppress("unused")
class Day01(input: String, context: RunContext = RunContext.PROD) : Day(input, context) {
    override fun solve() {
        var dialPos = 50
        var p1 = 0
        var p2 = 0
        lines.map {line ->
            val dir = line[0]
            var mag = line.substring(1).toInt()
            if (dir == 'R') mag *= -1
            pt(line, "")
            val newDir = dialPos + mag

            var diff = abs(newDir / 100)
            if (newDir == 0) { diff ++ } // ending on 0 is still landing on zero
            if (newDir < 0) { diff++ } // crossing through 0 lands on zero
            // but!! crossing through 0 *after* landing on zero doesn't count
            if (dialPos == 0 && newDir < 0) { diff-- }

            p2 += diff
            dialPos = newDir.mod(100)
            if (dialPos == 0) p1++
            plt(dialPos, if (diff != 0) diff else "")
        }
        a(p1, p2)
        // // going through again
        // dialPos = 50
        // p2 = 0
        // lines.map { line ->
        //     var lineDiff = 0;
        //     val dir = line[0]
        //     val mag = line.substring(1).toInt()
        //     pt(line, "")
        //     for (i in 1..mag) {
        //         if (dir == 'R') {
        //             (dialPos++)
        //         } else {
        //             (dialPos--)
        //         }
        //         if (dialPos == 100) dialPos = 0
        //         if (dialPos == -1) dialPos = 99
        //         if (dialPos == 0) {
        //             p2++
        //             lineDiff++
        //         }
        //     }
        //     plt(dialPos, if (lineDiff != 0) lineDiff else "")
        // }
    }
}
