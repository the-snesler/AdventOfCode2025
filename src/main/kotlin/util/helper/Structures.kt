package util.helper

object Structures {
    data class KDNode(val point: IntArray, val axis: Int, var left: KDNode? = null, var right:
    KDNode? = null) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as KDNode

            if (axis != other.axis) return false
            if (!point.contentEquals(other.point)) return false
            if (left != other.left) return false
            if (right != other.right) return false

            return true
        }

        override fun hashCode(): Int {
            var result = axis
            result = 31 * result + point.contentHashCode()
            result = 31 * result + (left?.hashCode() ?: 0)
            result = 31 * result + (right?.hashCode() ?: 0)
            return result
        }
    }

    class KDTree(val k: Int, var root: KDNode?) {
        companion object {
            fun build(points: List<IntArray>): KDTree {
                require(points.isNotEmpty()) { "points must not be empty" }
                val k = points[0].size
                points.forEach { p ->
                    require(p.size == k) { "all points must have the same dimension" }
                }
                fun buildRec(pts: List<IntArray>, depth: Int): KDNode? {
                    if (pts.isEmpty()) return null
                    val axis = depth % k
                    val sorted = pts.sortedBy { it[axis] }
                    val median = sorted.size / 2
                    val node = KDNode(sorted[median], axis)
                    node.left = buildRec(sorted.subList(0, median), depth + 1)
                    if (median + 1 <= sorted.lastIndex) node.right = buildRec(sorted.subList(median + 1, sorted.size), depth + 1)
                    return node
                }
                return KDTree(k, buildRec(points, 0))
            }
        }

        // squared Euclidean distance
        fun sqDist(a: IntArray, b: IntArray): Long {
            var s = 0L
            for (i in a.indices) {
                val d = a[i] - b[i]
                s += d * d
            }
            return s
        }

        // Nearest neighbor search. Returns the nearest point or null if tree empty.
        fun nearest(target: IntArray, ignorePoints: List<IntArray> = listOf()): IntArray? {
            require(target.size == k) { "target must have dimension $k" }
            var best: IntArray? = null
            var bestDist = Long.MAX_VALUE

            fun search(node: KDNode?) {
                if (node == null) return
                if (ignorePoints.isNotEmpty() && ignorePoints.any { it.contentEquals(node.point) }) {
                    // skip
                } else {
                    val d = sqDist(node.point, target)
                    if (d < bestDist) {
                        bestDist = d
                        best = node.point
                    }
                }
                val axis = node.axis
                val diff = target[axis] - node.point[axis]
                val (first, second) = if (diff <= 0) node.left to node.right else node.right to node.left
                search(first)
                if (diff * diff < bestDist) search(second)
            }

            search(root)
            return best
        }
    }
}
