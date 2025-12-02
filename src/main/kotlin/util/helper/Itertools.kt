// Thank you SizableShrimp https://github.com/SizableShrimp/AdventOfCode2023
/*
 * AdventOfCode2023
 * Copyright (C) 2023 SizableShrimp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package util.helper

import java.util.*
import java.util.stream.IntStream

// like Python Itertools
object Itertools {
    @SafeVarargs
    fun <T> product(vararg lists: List<T>): List<List<T>> {
        return product(1, *lists)
    }

    @SafeVarargs
    fun <T> product(repeat: Int, vararg lists: List<T>): List<List<T>> {
        var result: MutableList<List<T>> = ArrayList()
        result.add(ArrayList())
        var pools: List<List<T>> = ArrayList(listOf(*lists))
        pools = repeat(pools, repeat)
        for (pool in pools) {
            val newResult: MutableList<List<T>> = ArrayList()
            for (x in result) {
                for (y in pool) {
                    val list: MutableList<T> = ArrayList(x)
                    list.add(y)
                    newResult.add(list)
                }
            }
            result = newResult
        }
        return result
    }

    private fun <T> repeat(pools: List<List<T>>, repeat: Int): List<List<T>> {
        val result: MutableList<List<T>> = ArrayList()
        for (i in 0 until repeat) {
            for (pool in pools) {
                result.add(ArrayList(pool))
            }
        }
        return result
    }

    /**
     * Returns a List of combinations of the input collection, where each combination is of length r and sorted by
     * lexicographic order.
     *
     * @param collection The collection of elements to create combinations of.
     * @param r The size of each combination.
     * @param <T> The type of element in the input collection.
     * @return A List of combinations of the input collection, where each combination is of length r and sorted by
     * lexicographic order.
     * @see [Python itertools.combinations](https://docs.python.org/2/library/itertools.html.itertools.combinations)
    </T> */
    fun <T> combinations(collection: Collection<T>?, r: Int): List<List<T>> {
        val result: MutableList<List<T>> = ArrayList()
        val pool: List<T> = collection?.toList() ?: return result
        val n = pool.size
        val indices = IntStream.range(0, r).toArray()
        result.add(yieldResult(pool, indices))
        while (true) {
            var broke = false
            var last = 0
            for (i in IntStream.range(0, r).mapToObj<Int> { i: Int -> r - i - 1 }
                .toList()) {
                last = i
                if (indices[i] != i + n - r) {
                    broke = true
                    break
                }
            }
            if (!broke) return result
            indices[last]++
            IntStream.range(last + 1, r).forEach { j: Int ->
                indices[j] = indices[j - 1] + 1
            }
            result.add(yieldResult(pool, indices))
        }
    }

    private fun <T> yieldResult(pool: List<T>, indices: IntArray): List<T> {
        val result: MutableList<T> = ArrayList()
        for (index in indices) {
            result.add(pool[index])
        }
        return result
    }
}
